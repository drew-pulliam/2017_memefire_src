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
public class Aim extends Command {
	private double picTime;
	private double centerline;
	private double height;
	private double[] values;
	private double pixelsToDegrees=0.125;
	private int desiredCenterline=175;
	double tolerance = 10;
	double maxSpeed = 0.35;
	int count;

    public Aim() {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	requires(Robot.getLEDSolenoid());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.getLEDSolenoid().LEDOn();
    	count =0;
    	//Robot.getBallCameraThread().setRunning(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//values = Robot.getVisionCameraThread().getValues();
    	picTime = Robot.getBallCameraThread().getBallTime();//values[0];
    	centerline = Robot.getBallCameraThread().getBallCenterline();//values[1];
    	height = Robot.getBallCameraThread().getBallHeight();//values[2];
    	//int histIndex=Robot.getHistoryIndex();
    	//int picIndex = Robot.getIndexFromTime((long)picTime);
    	
		double leftRight = (centerline-desiredCenterline);
		SmartDashboard.putNumber("Aim.centerline", centerline);
		SmartDashboard.putNumber("leftRight: ", leftRight);
		double left = maxSpeed*(leftRight<0?-1:1);
		double right = maxSpeed*(leftRight<0?1:-1);
		Robot.getDriveBaseSimple().setLeftMotor(left);
		Robot.getDriveBaseSimple().setRightMotor(right);
		if(Math.abs(centerline - desiredCenterline)<=tolerance){
			count++;
		}else{
			count = 0;
		}
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return count > 0;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.getDriveBaseSimple().setLeftMotor(0.0);
		Robot.getDriveBaseSimple().setRightMotor(0.0);
		Robot.getLEDSolenoid().setFlashyMode(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
