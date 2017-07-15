package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;
import org.usfirst.frc.team4587.robot.commands.AimGearDrive.myPIDOutput;
import org.usfirst.frc.team4587.robot.commands.AimGearDrive.myPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Gyro;

/**
 *
 */
public class AimDist extends Command {
	private double height;
	private double[] values;
	private int desiredHeight=120;
	double tolerance = 5;
	double maxSpeed = 0.2;
	int count;
    public AimDist() {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	requires(Robot.getLEDSolenoid());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.getLEDSolenoid().LEDOn();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//values = Robot.getVisionCameraThread().getValues();
    	//picTime = values[0];
    	//centerline = values[1];
    	height = Robot.getBallCameraThread().getBallHeight();//values[2];


		double left = maxSpeed*(height<desiredHeight?-1:1);
		double right = maxSpeed*(height<desiredHeight?-1:1);
		Robot.getDriveBaseSimple().setLeftMotor(left);
		Robot.getDriveBaseSimple().setRightMotor(right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(height - desiredHeight)<=tolerance;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.getDriveBaseSimple().setLeftMotor(0.0);
		Robot.getDriveBaseSimple().setRightMotor(0.0);
		//Robot.getLEDSolenoid().LEDOff();
		Robot.getLEDSolenoid().setFlashyMode(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
