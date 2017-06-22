package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSet extends Command {

	double m_drive;
	double m_turn;
    public DriveSet(double drive, double turn) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	m_turn = turn;
    	m_drive = drive;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double driveStick = Robot.getOI().getDrive();// * Math.abs(Robot.getOI().getDrive());
    	double turnStick = Robot.getOI().getTurn();// * Math.abs(Robot.getOI().getTurn());
    	//Robot.getDriveBaseSimple().arcadeDrive(m_drive, m_turn);
    	Robot.getDriveBaseSimple().setLeftRightPower(m_turn, m_drive);
    	SmartDashboard.putNumber("drive stick", Robot.getOI().getDrive());
    	SmartDashboard.putNumber("turn stick", Robot.getOI().getTurn());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
