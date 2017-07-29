package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.StopFlywheel;
import org.usfirst.frc.team4587.robot.commands.ToggleFlywheelRunning;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Gyro;
import utility.LogDataSource;
import utility.ValueLogger;

/**
 *
 */
public class FlywheelPID extends PIDSubsystem  implements LogDataSource {

	public boolean on = false;
	private SpeedController m_flywheelMotor1;
	private SpeedController m_flywheelMotor2;
	//private SpeedController m_flywheelMotor3;
	private Encoder m_encoder;
	private double m_lastEncoders0 = 0.0;
	//private double m_lastEncoders1 = 0.0;
	//private double m_lastEncoders2 = 0.0;
	//private double m_lastEncoders3 = 0.0;
	//private double m_lastEncoders4 = 0.0;
	private long m_lastTime0;
	//private long m_lastTime1;
	//private long m_lastTime2;
	//private long m_lastTime3;
	//private long m_lastTime4;
	private double m_expectedMotorLevel;
	private double m_output;
	public void setExpectedMotorLevel(double motorLevel)
	{
		m_expectedMotorLevel = motorLevel;
	}
	public double getExpectedMotorLevel(){
		return m_expectedMotorLevel;
	}
	private double m_velocity;
	private boolean m_atSpeed;
	private double m_atSpeedTolerance = 100;
	public boolean atSpeed(){
		return m_atSpeed;
	}
	/*public void setLastEncoder(double lastEncoder)
	{
		m_lastEncoders = lastEncoder;
	}*/
	private static double m_kP = 0.007;//0.007
	private static double m_kI = 0.00025;//0.0001;
	private static double m_kD = 0.00225;//0.001;
	public double m_testSetPoint = 0.0;
	double encoderNow;
	long timeNow;
	
	private boolean m_running = false;
	public boolean running()
	{
		return m_running;
	}
	public void setRunning(boolean running)
	{
		m_running = running;
	}
	
    // Initialize your subsystem here
    public FlywheelPID() {
    	super(m_kP,m_kI,m_kD,0.025);
    	setSetpoint(0.0);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	setAbsoluteTolerance(2.0);
    	m_flywheelMotor1 = new Spark(RobotMap.MOTOR_FLYWHEEL_1);
    	m_flywheelMotor2 = new Spark(RobotMap.MOTOR_FLYWHEEL_2);
    	//m_flywheelMotor3 = new VictorSP(RobotMap.MOTOR_FLYWHEEL_3);
        m_encoder = new Encoder(RobotMap.ENCODER_FLYWHEEL_A, RobotMap.ENCODER_FLYWHEEL_B);
        
    }
    
    public double getVelocity()
    {
    	return (m_encoder.get() - m_lastEncoders0);
    }
    
    public void setSetpoint(double setpoint)
    {
    	super.setSetpoint(setpoint);
    }
    
    public void initialize()
    {
    	m_flywheelMotor1.set(0.0);
    	m_flywheelMotor2.set(0.0);
    	//m_flywheelMotor3.set(0.0);
    	setSetpoint(0.0);
    	m_lastTime0 = System.nanoTime();
    	//m_lastTime1 = m_lastTime0;
    	//m_lastTime2 = m_lastTime0;
    	//m_lastTime3 = m_lastTime0;
    	//m_lastTime4 = m_lastTime0;
    	m_lastEncoders0 = m_encoder.get();
    	//m_lastEncoders1 = m_lastEncoders0;
    	//m_lastEncoders2 = m_lastEncoders0;
    	//m_lastEncoders3 = m_lastEncoders0;
    	//m_lastEncoders4 = m_lastEncoders0;
    }
    
    public Encoder getEncoder()
    {
    	return m_encoder;
    }

    public void initDefaultCommand() {
    	//setDefaultCommand(new StopFlywheel());
    }

    
    protected double returnPIDInput() {
    	encoderNow = m_encoder.get();
    	timeNow = System.nanoTime();
    	m_velocity = ((encoderNow - m_lastEncoders0))/ (timeNow - m_lastTime0) * 60 * 1000000000 / 256;//  / 3 * 12 / 32/* 3ticks per rev * 12t on motor / 32t on flywheel*/;
    	//m_lastEncoders4 = m_lastEncoders3;
    	//m_lastEncoders3 = m_lastEncoders2;
    //	m_lastEncoders2 = m_lastEncoders1;
    //	m_lastEncoders1 = m_lastEncoders0;
    	m_lastEncoders0 = encoderNow;
    	//m_lastTime4 = m_lastTime3;
    	//m_lastTime3 = m_lastTime2;
    //	m_lastTime2 = m_lastTime1;
    //	m_lastTime1 = m_lastTime0;
    	m_lastTime0 = timeNow;
    	/*if (Math.abs(m_velocity - getSetpoint()) < 0.02 * getSetpoint())
    	{
    		m_expectedMotorLevel = (m_expectedMotorLevel + m_flywheelMotor1.get()) / 2;
    	}
    	if(m_velocity > getSetpoint()){
    		m_expectedMotorLevel = (m_expectedMotorLevel + m_flywheelMotor1.get()) / 2;
    	}*/
    	m_atSpeed = Math.abs(m_velocity-getSetpoint()) < m_atSpeedTolerance;
    	return m_velocity;
    	//encoder delta (velocity)
    }

    protected void usePIDOutput(double output) {
    	m_output = output;
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	if(getSetpoint() == 0.0)
    	{
        	m_flywheelMotor1.set(0.0);
        	m_flywheelMotor2.set(0.0);
        	//m_flywheelMotor3.set(0.0);
    	}
    	else
    	{
    		//m_flywheelMotor1.set(1.0);
    		//m_flywheelMotor2.set(-1.0);
    		double motorLevel = m_expectedMotorLevel + output;
    		if(motorLevel < 0)
    		{
    			motorLevel = 0;
    		}
    		else if(motorLevel > 1)
    		{
    			motorLevel = 1;
    		}
    		if(m_running){
    			m_flywheelMotor1.set(motorLevel);
    			m_flywheelMotor2.set(motorLevel*-1);
    		}else{
    			m_flywheelMotor1.set(0.0);
    			m_flywheelMotor2.set(0.0);
    		}
        	//m_flywheelMotor2.set(motorLevel);
        	//m_flywheelMotor3.set(motorLevel);
        //	System.out.println(motorLevel);
    	}
    	//m_flywheelMotor.set(0.8);
    	SmartDashboard.putNumber("flywheel actual motor level", m_flywheelMotor1.get());
    	SmartDashboard.putNumber("flywheel setpoint", getSetpoint());
    	SmartDashboard.putNumber("flywheel pid input", m_velocity);
    	SmartDashboard.putNumber("flywheel pid input again", m_velocity);
    	SmartDashboard.putNumber("flywheel expected motor level", m_expectedMotorLevel);
    	SmartDashboard.putNumber("flywheel pid output", output);
    }
    public void gatherValues ( ValueLogger logger )
    {
		logger.logDoubleValue ( "flywheel actual motor level", m_flywheelMotor1.get() );
    	logger.logDoubleValue ("flywheel setpoint", getSetpoint());
    	logger.logDoubleValue ("flywheel pid input", m_velocity);	
    	logger.logDoubleValue( "flywheel pid output", m_output );
    	logger.logDoubleValue("flywheel expected motor level", m_expectedMotorLevel);
    	logger.logDoubleValue( "flywheel encoder", m_encoder.get() );
    	logger.logDoubleValue( "flywheel encoder now", encoderNow );
    	logger.logDoubleValue( "flywheel encoder last", m_lastEncoders0 );
    	logger.logDoubleValue( "time now", timeNow );
    	logger.logDoubleValue( "time last", m_lastTime0 );
    }
}
