package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleGearIntakeMotors extends Command {


    public ToggleGearIntakeMotors() {
    	requires(Robot.getGearIntake());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	if(Robot.getGearIntake().motorOn())
    	{
    		Robot.getGearIntake().setGearIntakeMotor(0.0);
    	}
    	else
    	{
    		Robot.getGearIntake().setGearIntakeMotor(1.0);
    	}
    	Robot.getGearIntake().setLEDMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
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
    	end();
    }
}
