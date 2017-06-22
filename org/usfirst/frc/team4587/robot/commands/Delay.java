package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Delay extends Command {

	int m_delay;
	int m_count;
    public Delay(int delay) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_delay = delay;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_count++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_count >= m_delay;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
