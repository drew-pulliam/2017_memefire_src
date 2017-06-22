package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurretWithJoysticks extends Command {

    public TurretWithJoysticks() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getTurret());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.getTurret().setSetpoint(Robot.getTurret().getDegrees());
    	Robot.getTurret().disable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double turnStick = Robot.getOI().getTurn2() *0.2;//* Math.abs(Robot.getOI().getTurn2());
    	Robot.getTurret().setMotor(turnStick);
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
