package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDRingToggle extends Command {

	boolean on=false;
    public LEDRingToggle() {
    	requires(Robot.getLEDSolenoid());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	if(on){
    		Robot.getLEDSolenoid().LEDOff();
    		on = false;
    	}else{
    		Robot.getLEDSolenoid().LEDOn();
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
    	Robot.getBallIntake().setBallIntakeMotor(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
