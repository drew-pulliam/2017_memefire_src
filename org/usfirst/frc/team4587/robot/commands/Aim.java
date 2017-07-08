package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

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
	private double maxVelocity;
	private double maxAcceleration;
	private double picTime;
	private double centerline;
	private double height;
	private double currentHeading;
	private double desiredHeading;
	private double actualHeadingAtPictureTime;
	private double deltaAngleToBoiler;
	private double thisVelocity;
	private double thisDistance;
	private double lastDistance;
	private double thisTime;
	private double lastTime;
	private double turnDistanceToGoal;
	private double[] values;
	private double pixelsToDegrees=0.125;
	private int desiredCenterline=160;
	

    public Aim() {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	maxVelocity=8.0;
    	maxAcceleration=6.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentHeading=Gyro.getYaw();
    	thisTime = System.nanoTime();
    	values = Robot.getVisionCameraThread().getValues();
    	picTime = values[0];
    	centerline = values[1];
    	height = values[2];
    	deltaAngleToBoiler=(centerline-desiredCenterline)*pixelsToDegrees;
    	
    	//actualHeadingAtPictureTime = ;
    	desiredHeading = actualHeadingAtPictureTime + deltaAngleToBoiler;
    	
    	double degreesToTurn = desiredHeading-currentHeading;
    	if(Math.abs(degreesToTurn)>=180){
    		//if we're turning > 180 degrees it would be faster to turn the other direction
    		if(degreesToTurn<0){
    			degreesToTurn+=360;
    		}else{
    			degreesToTurn-=360;
    		}
    	}
    	turnDistanceToGoal = degreesToTurn * Math.PI * 4 / 360;
    	//thisDistance = ;
    	
    	lastDistance = thisDistance;
    	lastTime=thisTime;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.getDriveBaseSimple().arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
