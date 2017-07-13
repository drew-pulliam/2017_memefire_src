package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class KillEverything extends Command {

    public KillEverything(int delay) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getBallIntake());
    	requires(Robot.getDriveBaseSimple());
    	requires(Robot.getFeeder());
    	requires(Robot.getFlywheel());
    	requires(Robot.getGearIntake());
    	requires(Robot.getHotDogs());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.getBallIntake().setBallIntakeMotor(0.0);
    	Robot.getDriveBaseSimple().setLeftRightPower(0.0, 0.0);
    	Robot.getFeeder().setFeederMotor(0.0);
    	Robot.getFlywheel().setRunning(false);
		Robot.getFlywheel().setSetpoint(0.0);
		Robot.getFlywheel().disable();
		Robot.getGearIntake().setGearIntakeMotor(0.0);
		Robot.getHotDogs().setHotDogMotors(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    }
}
