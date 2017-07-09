package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import utility.Bling;

/**
 *
 */
public class AutoGearIntakeMotors extends Command {


	int count;
	int count2;
	boolean finish;
	boolean x;
	boolean hasStalled;
    public AutoGearIntakeMotors() {
    	requires(Robot.getGearIntake());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.getGearIntake().gearIntakeDown();
    	Robot.getGearIntake().setGearIntakeMotor(-1.0);
    	Robot.getGearIntake().setGearIsLoaded(false);
    	count = 0;
    	count2 = 0;
    	finish = false;
    	hasStalled = false;
    	Robot.getGearIntake().setLEDMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if (Robot.getGearIntake().isStalling())
    	{
    		if(count >= 10){//50 = 1sec
            	Robot.getGearIntake().setGearIsLoaded(true);
            	Robot.getGearIntake().gearIntakeUp();
            	Robot.getLEDSolenoid().LEDOn();
    		}
        	hasStalled = true;
        	count2 = 0;
    	}
    	else if (hasStalled && (Robot.getGearIntake().isGearLoaded() == false))
    	{
    		count2++;
    		if(count2 >= 5)
    		{
    			Robot.getGearIntake().setGearIsLoaded(false);
    			hasStalled = false;
    			count = 0;
    			count2 = 0;
    		}
    	}
    	if(hasStalled){
    		count++;
    		Bling.sendData((byte)65);
    	}
    	if(count >= 24)
    	{
        	Robot.getGearIntake().setLEDMode();
        	Robot.getGearIntake().setGearIntakeMotor(0.0);
        	//Robot.getGearIntake().setLEDMode();
        	finish = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finish;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.getGearIntake().setLEDMode();
    	//Robot.getGearIntake().gearIntakeUp();
    	Robot.getLEDSolenoid().LEDOff();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
