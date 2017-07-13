package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDRingDefaultCommand extends Command {

	boolean on=false;
	int count;
	int pulseLength=10;
	int pulses = 5;
	boolean flashyMode;
    public LEDRingDefaultCommand() {
    	requires(Robot.getLEDSolenoid());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	count=pulseLength;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	flashyMode = Robot.getLEDSolenoid().getFlashyMode();
    	if(flashyMode){
    		count++;
    		if(count%pulseLength==0){
    			if(Robot.getLEDSolenoid().getLEDOn()){
    				Robot.getLEDSolenoid().LEDOff();
    			}else{
    				Robot.getLEDSolenoid().LEDOn();
    			}
    		}
    		if(count>pulseLength*pulses){
    			flashyMode = false;
        		count=pulseLength;
        		Robot.getLEDSolenoid().LEDOff();
    		}
    	}else{
    		count=pulseLength;
    		Robot.getLEDSolenoid().LEDOff();
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
