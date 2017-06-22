package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EjectGear extends Command {

	private boolean m_motorOn = false;
	private int count;
	private int encoderStart;

    public EjectGear() {
    	requires(Robot.getGearIntake());
    	requires(Robot.getDriveBaseSimple());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.getGearIntake().gearIntakeDown();
    	count = 0;
    	encoderStart = Robot.getDriveBaseSimple().getEncoderLeft();
    	Robot.getGearIntake().setLEDMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	count++;
    	int encoder = Robot.getDriveBaseSimple().getEncoderLeft();
    	if(count > 5)
    	{
        	Robot.getGearIntake().setGearIntakeMotor(-1.0);
    		Robot.getDriveBaseSimple().arcadeDrive(-0.7, 0.0);
    	}
    	if(encoder - encoderStart <= -150)
    	{
    		Robot.getDriveBaseSimple().arcadeDrive(0.05, 0.0);
    	}
    	Robot.getGearIntake().setGearIsLoaded(false);

    	Robot.getGearIntake().setLEDMode();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return count>30;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.getGearIntake().setGearIntakeMotor(0.0);
    	//Robot.getGearIntake().gearIntakeUp();
    	Robot.getDriveBaseSimple().arcadeDrive(0.0, 0.0);
    	Robot.getGearIntake().setLEDMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
