package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartTurretMotors extends Command {
	
	public double motorLevel;

    public StartTurretMotors(double motorLevel) {
    	this.motorLevel = motorLevel;
    	requires(Robot.getTurret());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.getTurret().setTurretMotorTarget(motorLevel);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	//Robot.getTurret().updateTurretMotor();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.getTurret().setTurretMotorTarget(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
