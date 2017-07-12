package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShintakeAndHotDogsToggle extends Command {


	boolean on = false;
    public ShintakeAndHotDogsToggle() {
    	requires(Robot.getHotDogs());
    	requires(Robot.getFeeder());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	if(on){
    		Robot.getHotDogs().setHotDogMotors(0.0);
    		Robot.getFeeder().setFeederMotor(0.0);
    		on = false;
    	}else{
    		Robot.getHotDogs().setHotDogMotors(1.0);
    		Robot.getFeeder().setFeederMotor(1.0);
    		on = true;
    	}
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
