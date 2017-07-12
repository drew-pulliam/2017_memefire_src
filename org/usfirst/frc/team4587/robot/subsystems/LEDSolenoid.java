package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.LEDRingDefaultCommand;

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
public class LEDSolenoid extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Solenoid m_LEDRing;
	boolean flashyMode=false;
	public boolean getFlashyMode(){
		return flashyMode;
	}
	public void setFlashyMode(boolean x){
		flashyMode = x;
	}

	public boolean getLEDOn(){
		return m_LEDRing.get()==true;
	}
	public void LEDOn(){
		m_LEDRing.set(true);
	}
	public void LEDOff(){
		m_LEDRing.set(false);
	}
	
    public LEDSolenoid()
    {    	
    	m_LEDRing = new Solenoid(RobotMap.SOLENOID_LED_RING);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new LEDRingDefaultCommand());
    }
}