package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UpdateLEDs extends Command {


    public UpdateLEDs() {
    	requires(Robot.getGearIntake());
    }
    int count;
    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	count=0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	count++;
    	if(count>=25){
    		Robot.getGearIntake().setLEDMode();
    		count = 0;
    	}
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
    	end();
    }
}
