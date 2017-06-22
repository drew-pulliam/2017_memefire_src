package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleGearIntakeUpDown extends Command {


    public ToggleGearIntakeUpDown() {
    	requires(Robot.getGearIntake());
    }
    int count;

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	if(Robot.getGearIntake().getPiston() == false)
    	{
    		Robot.getGearIntake().gearIntakeUp();
    	}
    	else
    	{
    		Robot.getGearIntake().gearIntakeDown();
    	}
    	count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	count++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return count>=10;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.getGearIntake().setLEDMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
