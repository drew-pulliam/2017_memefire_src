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
public class Feeder extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_feederMotor;
	private boolean m_isClimbing = false;
	public void setFeederMotor(double x)
	{
		m_feederMotor.set(x);
	}

    public Feeder()
    {    	
    	m_feederMotor = new VictorSP(RobotMap.MOTOR_FEEDER);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}