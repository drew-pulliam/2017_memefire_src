package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.Robot;
import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.GearIntakeLEDs;
import org.usfirst.frc.team4587.robot.commands.UpdateLEDs;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import utility.Bling;
import utility.LogDataSource;
import utility.RampedSpeedController;
import utility.ValueLogger;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class GearIntake extends Subsystem implements LogDataSource {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_gearIntakeMotor;
	private Solenoid m_gearIntakePiston;
	public void gearIntakeDown()
	{
		m_gearIntakePiston.set(false);
	}
	public void gearIntakeUp()
	{
		m_gearIntakePiston.set(true);
	}
	public boolean getPiston()
	{
		return m_gearIntakePiston.get();
	}
    public void setGearIntakeMotor(double x)
    {
    	m_gearIntakeMotor.set(-1 * x);
    	m_motorOn = !(Math.abs(x) < 0.01);
    	
    }
    public boolean isStalling(){
    	return Robot.getPDP().getCurrent(RobotMap.PDP_PORT_GEAR_INTAKE_MOTOR) >= RobotMap.GEAR_INTAKE_STALL_CURRENT;
    	//return false;
    }

    private boolean m_motorOn;
    public boolean motorOn()
    {
    	return m_motorOn;
    }

    private boolean gearIsLoaded = false;
    public boolean isGearLoaded()
    {
    	/*if ( gearIsLoaded == false ) {
    		if ( getGearIntakeSwitch() == false ) {
    			gearIsLoaded = true;
    		}
    	}*/
    	return gearIsLoaded;
    }
    public void setGearIsLoaded ( boolean x )
    {
    	gearIsLoaded = x;
    }
    
    /*public void setLEDMode(){
    	if(m_motorOn){
    		Robot.writeToArduino((byte)68);
    	}else{
    		if(gearIsLoaded){
    			if(m_gearIntakePiston.get()){
    				Robot.writeToArduino((byte)66);
    			}else{
    				Robot.writeToArduino((byte)65);
    			}
    		}else{
    			if(m_gearIntakePiston.get()){
    				Robot.writeToArduino((byte)69);
    			}else{
    				Robot.writeToArduino((byte)67);
    			}
    		}
    	}
    }*/
    public void setLEDMode(){
    	if(m_motorOn){
    		Bling.sendData((byte)68);
    	}else{
    		if(gearIsLoaded){
    			if(m_gearIntakePiston.get()){
    	    		Bling.sendData((byte)66);
    			}else{
    	    		Bling.sendData((byte)65);
    			}
    		}else{
    			if(m_gearIntakePiston.get()){
    	    		Bling.sendData((byte)69);
    			}else{
    	    		Bling.sendData((byte)67);
    			}
    		}
    	}
    }

    public GearIntake()
    {    	
        m_gearIntakeMotor = new Spark(RobotMap.MOTOR_GEAR_INTAKE);
        m_gearIntakePiston = new Solenoid(RobotMap.SOLENOID_GEAR_INTAKE);

        m_motorOn = false;
    }
    
    public void initialize()
    {
    	gearIsLoaded = false;

    }
    
    public void gatherValues( ValueLogger logger)
    {
    	//m_gearIntakeMotor.gatherValues(logger);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new GearIntakeLEDs());
    	setDefaultCommand(new UpdateLEDs());
    }
}

