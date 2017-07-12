package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootBalls extends Command {


	boolean on = false;
	boolean end = false;
	int count;
	int recoveryTime=30;//units = 20ms cycles
	double flywheelExpectedMotor;
	double motorLevelBoost=0.25;
    public ShootBalls() {
    	requires(Robot.getHotDogs());
    	requires(Robot.getFeeder());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	count=0;
    	end = false;
    	if(on){
    		Robot.getHotDogs().setHotDogMotors(0.0);
    		Robot.getFeeder().setFeederMotor(0.0);
    		on = false;
    		end = true;
    	}else{
    		flywheelExpectedMotor = Robot.getFlywheel().getExpectedMotorLevel();
    		Robot.getFlywheel().setExpectedMotorLevel(flywheelExpectedMotor+motorLevelBoost);
    		Robot.getHotDogs().setHotDogMotors(1.0);
    		Robot.getFeeder().setFeederMotor(1.0);
    		on = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(on){
	    	count++;
	    	if(count>=recoveryTime){
	    		Robot.getFlywheel().setExpectedMotorLevel(flywheelExpectedMotor);
	    		end = true;
	    	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return end;
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
