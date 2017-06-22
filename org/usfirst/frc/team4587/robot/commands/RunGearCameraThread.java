package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.GearCameraThread;

/**
 *
 */
public class RunGearCameraThread extends Command {

	private GearCameraThread m_gearCameraThread;
	boolean m_humanVision;
    public RunGearCameraThread(boolean humanVision) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_humanVision = humanVision;
    	m_gearCameraThread = new GearCameraThread();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(m_gearCameraThread.isRunning() == false)
    	{
	    	System.out.println("Camera thread start");
	    	m_gearCameraThread.setRunning(true);
	    	m_gearCameraThread.start();
    	}
    	if(m_humanVision){
    		m_gearCameraThread.setMode("HumanVision");
    	}
    	else
    	{
    		m_gearCameraThread.setMode("ComputerVision");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
