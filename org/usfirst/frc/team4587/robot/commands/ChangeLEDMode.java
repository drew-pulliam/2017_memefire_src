package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Bling;

/**
 *
 */
public class ChangeLEDMode extends Command {

	byte m_mode;
    public ChangeLEDMode(byte mode) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_mode = mode;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.writeToArduino(m_mode);
    	System.out.println("hello");
    	SmartDashboard.putBoolean("HELLO", true);
    	Bling.sendData(m_mode);
    	System.out.println("goodbye");
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
