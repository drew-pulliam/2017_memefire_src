package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.CheesyDriveHelper;

/**
 *
 */
public class DriveCheesyWithJoysticks extends Command {

    public DriveCheesyWithJoysticks() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean quickTurn;
    	double driveStick = Robot.getOI().getDrive();
    	double turnStick = Robot.getOI().getTurn();
    	if(Math.abs(turnStick) <= 0.02){
    		turnStick = 0.0;
    	}else{
    		if(turnStick >= 0.02){
            	turnStick = (turnStick*0.8)+0.2;
    		}else if(turnStick <= -0.02){
            	turnStick = (turnStick*0.8)-0.2;
    		}
    	}
    	if(Math.abs(driveStick) <= 0.02){
    		quickTurn = true;
    		driveStick = 0.0;
    		turnStick *= Math.abs(turnStick);
    	}else{
    		quickTurn = false;
    		if(driveStick >= 0.02){
    	    	driveStick = (driveStick*0.95)+0.05;
    		}else if(driveStick <= -0.02){
    	    	driveStick = (driveStick*0.95)-0.05;
    		}
    	}
    	//quickTurn = false;
    	CheesyDriveHelper cdh = new CheesyDriveHelper(Robot.getDriveBaseSimple());
    	cdh.cheesyDrive(driveStick, turnStick, quickTurn, false);
    	SmartDashboard.putNumber("drive stick", Robot.getOI().getDrive());
    	SmartDashboard.putNumber("turn stick", Robot.getOI().getTurn());
    	SmartDashboard.putNumber("drive stick modified", driveStick);
    	SmartDashboard.putNumber("turn stick modified", turnStick);
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
