package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Bling;

/**
 *
 */
public class ClimbMotorToggle extends Command {

	boolean end;
    public ClimbMotorToggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getClimbMotor());
    	//requires(Robot.getFlywheel());
    	//requires(Robot.getScytheAndShintake());
    	//requires(Robot.getGearIntake());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	end = true;
    	if(Robot.getClimbMotor().isClimbing())
    	{
    		Robot.getClimbMotor().stopClimb();
    		Robot.getClimbMotor().setClimbing(false);
    	}
    	else
    	{
    		//Robot.getFlywheel().initialize();
    		//Robot.getScytheAndShintake().setScytheMotor(0.0);
    		//Robot.getScytheAndShintake().setShintakeMotor(0.0);
    		//Robot.getGearIntake().setGearIntakeMotor(0.0);
    		Robot.getClimbMotor().startClimb();
    		Robot.getClimbMotor().setClimbing(true);
    		Bling.sendData((byte)65);
    		//end = false;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Bling.sendData((byte)65);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return end;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Bling.sendData((byte)69);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
