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
public class HotDogs extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_hotDogMotor0;
	private SpeedController m_hotDogMotor1;
	public void setHotDogMotors(double x)
	{
		m_hotDogMotor0.set(x);
		m_hotDogMotor1.set(x);
	}
    public HotDogs()
    {    	
    	m_hotDogMotor0 = new VictorSP(RobotMap.MOTOR_HOTDOGS_1);
    	m_hotDogMotor1 = new Spark(RobotMap.MOTOR_HOTDOGS_2);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}