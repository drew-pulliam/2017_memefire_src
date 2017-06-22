package utility;

//import edu.wpi.first.wpilibj.CANJaguar;
//import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SD540;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

public class RampedSpeedController implements SpeedController, LogDataSource
{
    // This class wraps a SpeedController.
    // It limits the rate at which the power level can be changed.
    // To set a new desired level, call setDesiredSetting().
    // Make sure that some command is calling updateMotorLevel() on every Scheduler run.

    public enum ControllerType {
        CANJaguar, CANTalon, Jaguar, SD540, Spark, Talon, TalonSRX, Victor, VictorSP
    };

    // Implement the abstract SpeedController interface methods.
    public void pidWrite ( double output ) {
        setDesiredSetting ( output );
    }
    public void disable() {
        if ( m_controller != null ) m_controller.disable();
    }
    public double get() {
        return getDesiredSetting();
    }
    public boolean getInverted() {
        return (m_controller == null) ? false : m_controller.getInverted();
    }
    public void set ( double speed ) {
        setDesiredSetting ( speed );
    }
    public void set ( double speed, byte syncGroup ) {
        setDesiredSetting ( speed );
        //TODO: What should we do with the syncGroup value?
    }
    public void setInverted ( boolean isInverted ) {
        if ( m_controller != null ) m_controller.setInverted ( isInverted );
    }
    public void stopMotor() {
        setDesiredSetting ( 0.0 );
        m_lastSetting = 0.0;
        if ( m_controller != null ) m_controller.set ( 0.0 );
    }

    private SpeedController m_controller;
    private double m_desiredSetting = 0.0;
    private double m_lastSetting = 0.0;
    private double m_maxRaisePerInterval = 0.2;
    private double m_maxLowerPerInterval = 0.4;
    private double m_positiveDeadband = 0.0;
    private double m_negativeDeadband = 0.0; // Note: this is expressed as an absolute value.
    private String m_name;

    public RampedSpeedController ( ControllerType controllerType, int pwmPortNumber )
    {
        switch ( controllerType ) {
            //case CANJaguar: m_controller = new CANJaguar(pwmPortNumber); break;
            //case CANTalon:  m_controller = new CANTalon(pwmPortNumber); break;
            case Jaguar:    m_controller = new Jaguar(pwmPortNumber); break;
            case SD540:     m_controller = new SD540(pwmPortNumber); break;
            case Spark:     m_controller = new Spark(pwmPortNumber); break;
            case Talon:     m_controller = new Talon(pwmPortNumber); break;
            case TalonSRX:  m_controller = new TalonSRX(pwmPortNumber); break;
            case Victor:    m_controller = new Victor(pwmPortNumber); break;
            case VictorSP:  m_controller = new VictorSP(pwmPortNumber); break;
            default: {
                System.out.println("Unrecognized ControllerType!"+controllerType.toString());
            }
        }
        if ( m_controller != null ) m_controller.set(0.0);
    }

    public String getName() { return m_name; }
    public void setName(String str) { m_name = str; }
    
    public double getDesiredSetting() { return m_desiredSetting; }
    public void setDesiredSetting(double x) { m_desiredSetting = x; }

    public double getLastSetting() { return m_lastSetting; }

    public double getMaxRaisePerInterval() { return m_maxRaisePerInterval; }
    public void setMaxRaisePerInterval(double x) { m_maxRaisePerInterval = x; }

    public double getMaxLowerPerInterval() { return m_maxLowerPerInterval; }
    public void setMaxLowerPerInterval(double x) { m_maxLowerPerInterval = x; }

    public double getPositiveDeadband() { return m_positiveDeadband; }
    public void setPositiveDeadband(double x) { m_positiveDeadband = x; }

    public double getNegativeDeadband() { return m_negativeDeadband; }
    public void setNegativeDeadband(double x) { m_negativeDeadband = x; }
    
    public double getMotorLevel() { return (m_controller == null) ? -10.0 : m_controller.get(); }

    public void updateMotorLevel()
    {
        if ( m_controller == null ) return;
        if ( m_desiredSetting == m_lastSetting ) return;
        double new_setting = m_desiredSetting;
        if ( (m_desiredSetting * m_lastSetting) < 0 ) {
            // The desired setting and last setting are opposite sides of zero.

            // The maximum amount that can be moved in this case is limited by the "raise" limit.
            // This makes sense - we are definitely accelerating from one direction to the other, even if the
            // absolute value of the new setting is less than the previous setting's absolute value.

            // Determine how much we are trying to move, taking into account deadband.
            double amount_trying_to_move = 0;
            if ( m_desiredSetting > m_positiveDeadband ) {
                amount_trying_to_move += m_desiredSetting - m_positiveDeadband;
            } else if ( m_desiredSetting < (-1 * m_negativeDeadband) ) {
                amount_trying_to_move += Math.abs(m_desiredSetting) - m_negativeDeadband;
            }
            if ( m_lastSetting > m_positiveDeadband ) {
                amount_trying_to_move += m_lastSetting - m_positiveDeadband;
            } else if ( m_lastSetting < (-1 * m_negativeDeadband) ) {
                amount_trying_to_move += Math.abs(m_lastSetting) - m_negativeDeadband;
            }

            // If we are not trying to move too much, just go to the desired setting.
            // Otherwise, move maxRaisePerInterval in the direction needed.
            if ( amount_trying_to_move <= m_maxRaisePerInterval ) {
                new_setting = m_desiredSetting;
            } else {
                if ( m_desiredSetting < m_lastSetting ) {
                    new_setting = m_lastSetting - m_maxRaisePerInterval;
                } else {
                    new_setting = m_lastSetting + m_maxRaisePerInterval;
                }
            }
        } else {
            // The desired setting and last setting are both on the same side of zero.

            // If both are non-positive, use the negative deadband; else use the positive deadband.
            double deadband = ((m_desiredSetting + m_lastSetting) < 0)
                            ? m_negativeDeadband
                            : m_positiveDeadband;

            if ( Math.abs(m_desiredSetting) > Math.abs(m_lastSetting) ) {
                // We are trying to accelerate the motor (i.e. moving away from zero).
                // Determine how far we are trying to move the setting, but ignore any deadband region.
                double delta = Math.abs(m_desiredSetting) - Math.max(Math.abs(m_lastSetting),deadband);
                // If this is greater than the maximum we can raise in one shot, limit ourselves to that.
                if ( delta > m_maxRaisePerInterval ) {
                    new_setting = Math.max(Math.abs(m_lastSetting),deadband) + m_maxRaisePerInterval;
                    if ( m_desiredSetting < 0 ) new_setting *= -1;
                } else {
                    new_setting = m_desiredSetting;
                }
            } else {
                // We are trying to decelerate the motor (i.e. moving toward zero).
                // Start off assuming we'll move the maximum amount we are allowed.
                new_setting = Math.abs(m_lastSetting) - m_maxLowerPerInterval;
                if ( new_setting <= Math.abs(m_desiredSetting) ) {
                    // We are not trying to move more than the maxLowerPerInterval setting.
                    new_setting = m_desiredSetting;
                } else if ( new_setting <= deadband ) {
                    // We are moving into the deadband, so go ahead all the way.
                    new_setting = m_desiredSetting;
                } else {
                    if ( m_desiredSetting < 0 ) new_setting *= -1;
                }
            }
        }
        m_controller.set(new_setting);
        m_lastSetting = new_setting;
    }

    
   
    
    public String getHeaders ( String motorName )
    {
        StringBuffer sb = new StringBuffer();
        sb            .append(motorName).append(" Desired Level")
          .append(",").append(motorName).append(" Last Level")
          .append(",").append(motorName).append(" Current Setting")
          .append(",").append(motorName).append(" Max Raise")
          .append(",").append(motorName).append(" Max Lower")
          .append(",").append(motorName).append(" Pos. Deadband")
          .append(",").append(motorName).append(" Neg. Deadband")
        ;
        return sb.toString();
    }
    
    public void gatherValues(ValueLogger logger)
    {
    	logger.logDoubleValue(m_name + " Desired Level", getDesiredSetting());
    	logger.logDoubleValue(m_name + " Last Level", getLastSetting());
    	logger.logDoubleValue(m_name + " Current Setting", getMotorLevel());
    	logger.logDoubleValue(m_name + " Max Raise Per Interval", m_maxRaisePerInterval);
    	logger.logDoubleValue(m_name + " Max Lower Per Interval", m_maxLowerPerInterval);
    	logger.logDoubleValue(m_name + " Positive Deadband", m_positiveDeadband);
    	logger.logDoubleValue(m_name + " Negative Deadband", m_negativeDeadband);
    }

    /*public String getValues ( String motorName )
    {
        StringBuffer sb = new StringBuffer();
        Parameters.displayDouble ( motorName+" Desired Level",   getDesiredSetting(), sb );
        Parameters.displayDouble ( motorName+" Last Level",      getLastSetting(), sb );
        Parameters.displayDouble ( motorName+" Current Setting", getMotorLevel(), sb );
        sb.append(",").append(m_maxRaisePerInterval)
          .append(",").append(m_maxLowerPerInterval)
          .append(",").append(m_positiveDeadband)
          .append(",").append(m_negativeDeadband);
        return sb.toString().substring(1);
    }*/
}
