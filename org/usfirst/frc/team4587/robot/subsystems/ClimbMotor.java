package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import utility.LogDataSource;
import utility.RampedSpeedController;
import utility.ValueLogger;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class ClimbMotor extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_climbMotor0;
	//private SpeedController m_climbMotor1;
	private boolean m_isClimbing = false;
	public void startClimb()
	{
		m_climbMotor0.set(1);
		//m_climbMotor1.set(1);
	}
	public void stopClimb()
	{
		m_climbMotor0.set(0);
		//m_climbMotor1.set(0);
	}
	public boolean isClimbing(){
		return m_isClimbing;
	}
	public void setClimbing(boolean climbing){
		m_isClimbing = climbing;
	}

    public ClimbMotor()
    {    	
    	m_climbMotor0 = new VictorSP(RobotMap.MOTOR_CLIMB_1);
    	//m_climbMotor1 = new Spark(RobotMap.MOTOR_CLIMB_2);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}