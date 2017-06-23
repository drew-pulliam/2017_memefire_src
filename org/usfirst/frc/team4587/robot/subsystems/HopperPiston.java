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
public class HopperPiston extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Solenoid m_hopperPiston;
	public void setHopperPiston(boolean x)
	{
		m_hopperPiston.set(x);
	}

    public HopperPiston()
    {    	
    	m_hopperPiston = new Solenoid(RobotMap.SOLENOID_HOPPER);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}