package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSimpleWithJoysticks extends Command {

    public DriveSimpleWithJoysticks() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double driveStick = Robot.getOI().getDrive();// * Math.abs(Robot.getOI().getDrive());
    	double turnStick = Robot.getOI().getTurn();// * Math.abs(Robot.getOI().getTurn());
    	if(Math.abs(turnStick) <= 0.1){
    		turnStick = 0.0;
    	}
    	if(Math.abs(driveStick) <= 0.1){
    		driveStick = 0.0;
    	}
    	Robot.getDriveBaseSimple().arcadeDrive(driveStick, turnStick);
    	SmartDashboard.putNumber("drive stick", Robot.getOI().getDrive()*0.3);
    	SmartDashboard.putNumber("turn stick", Robot.getOI().getTurn()*0.3);
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
