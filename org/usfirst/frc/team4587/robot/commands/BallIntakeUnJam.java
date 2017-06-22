package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BallIntakeUnJam extends Command {


    public BallIntakeUnJam() {
    	requires(Robot.getBallIntake());
    	requires(Robot.getHopperAndShintake());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.getBallIntake().setBallIntakeMotor(-1.0);
    	Robot.getHopperAndShintake().setHopperMotor(0.0);
    	//Robot.getHopperAndShintake().setShintakeMotor(-1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	//Robot.getBallIntake().setBallIntakeMotor(-1.0);
    	//Robot.getHopperAndShintake().setHopperMotor(-1.0);
    	//Robot.getHopperAndShintake().setShintakeMotor(-1.0);
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
