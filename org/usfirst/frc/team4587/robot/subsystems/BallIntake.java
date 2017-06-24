package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.Robot;
import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.GearIntakeLEDs;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import utility.LogDataSource;
import utility.RampedSpeedController;
import utility.ValueLogger;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class BallIntake extends Subsystem implements LogDataSource {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_ballIntakeMotor;
	private SpeedController m_ballIntakeMotor2;
    public void setBallIntakeMotor(double x)
    {
    	m_ballIntakeMotor.set(-1 * x);
    	m_ballIntakeMotor2.set(-1 * x);
    	m_motorOn = !(Math.abs(x) < 0.01);
    	
    }

    private Solenoid m_ballIntakePiston;
    /*public void setBallIntakePiston(boolean x){
    	m_ballIntakePiston.set(x);
    }*/
    public void lowerBallIntake(){
    	m_ballIntakePiston.set(true);
    }
    
    private boolean m_motorOn;
    public boolean motorOn()
    {
    	return m_motorOn;
    }

    

    public BallIntake()
    {    	
        m_ballIntakeMotor = new Spark(RobotMap.MOTOR_BALL_INTAKE);
        m_ballIntakeMotor2 = new Spark(RobotMap.MOTOR_BALL_INTAKE_2);
        m_ballIntakePiston = new Solenoid(RobotMap.SOLENOID_BALL_INTAKE);
        m_motorOn = false;
    }
    
    public void initialize()
    {
    	//gearIsLoaded = false;

    }
    
    public void gatherValues( ValueLogger logger)
    {
    	//logger.logBooleanValue("Intake Switch", getGearIntakeSwitch());
    	//m_gearIntakeMotor.gatherValues(logger);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new GearIntakeLEDs());
    }
}

