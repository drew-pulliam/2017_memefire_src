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
	private double deltaAngleToBoiler=0.0;
	private double turnDistanceToGoal;
	private double[] values;
	private double pixelsToDegrees=0.125;
	private int desiredCenterline=160;
	private double wheelBase;
	double Ka = 0.05;
	double Kv = 0.4/43;
	double tolerance = 10;
	double currentLeftVel;
	double currentRightVel;
	int count;

    public Aim() {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	requires(Robot.getLEDSolenoid());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Interrupted?", "no");
    	SmartDashboard.putString("end?", "no");
    	Robot.getLEDSolenoid().LEDOn();
    	maxVelocity=8.0;
    	maxAcceleration=6.0;
    	wheelBase = 28.0/12.0;
    	count =0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("aimCount", count);
    	count+=1;
    	System.out.println("aiming");
    	currentHeading=Gyro.getYaw();
    	values = Robot.getVisionCameraThread().getValues();
    	picTime = values[0];
    	centerline = values[1];
    	height = values[2];
    	int histIndex=Robot.getHistoryIndex();
    	int picIndex = Robot.getIndexFromTime((long)picTime);
    	if(centerline>=0){
    		deltaAngleToBoiler=(centerline-desiredCenterline)*pixelsToDegrees;
        	actualHeadingAtPictureTime = Robot.getHeading(picIndex);
        	desiredHeading = actualHeadingAtPictureTime + deltaAngleToBoiler;
    	}

    	SmartDashboard.putNumber("aimCount", count);
    	count+=1;
    	
    	
    	double degreesToTurn = desiredHeading-currentHeading;
    	if(Math.abs(degreesToTurn)>=180){
    		//if we're turning > 180 degrees it would be faster to turn the other direction
    		if(degreesToTurn<0){
    			degreesToTurn+=360;
    		}else{
    			degreesToTurn-=360;
    		}
    	}
    	SmartDashboard.putNumber("degreesToTurn", degreesToTurn);
    	if(Math.abs(degreesToTurn)<tolerance*pixelsToDegrees){
    		Robot.getDriveBaseSimple().setLeftMotor(0.0);
    		Robot.getDriveBaseSimple().setRightMotor(0.0);
        	SmartDashboard.putNumber("aimCount", count);
        	count+=1;
    		return;
    	}
    	turnDistanceToGoal = degreesToTurn * Math.PI * wheelBase / 360;

    	double[] leftAccAndVel = findAccAndVel(Robot.getLeftEncoder(histIndex),Robot.getLeftEncoder(histIndex-1),Robot.getLeftEncoder(histIndex-2),turnDistanceToGoal);
    	double[] rightAccAndVel = findAccAndVel(Robot.getRightEncoder(histIndex),Robot.getRightEncoder(histIndex-1),Robot.getRightEncoder(histIndex-2),turnDistanceToGoal*-1);
    	//thisDistance = ;
    	currentLeftVel = leftAccAndVel[2];
    	currentRightVel = rightAccAndVel[2];
    	
    	double leftMotorLevel =  Ka * leftAccAndVel[0] + Kv * leftAccAndVel[1];
    	double rightMotorLevel = Ka * rightAccAndVel[0] + Kv * rightAccAndVel[1];

    	Robot.getDriveBaseSimple().setLeftMotor(leftMotorLevel);
    	Robot.getDriveBaseSimple().setRightMotor(rightMotorLevel);
    	SmartDashboard.putNumber("LeftMotorLevel: ", leftMotorLevel);
    	SmartDashboard.putNumber("RightMotorLevel: ", rightMotorLevel);
    	SmartDashboard.putNumber("rightAcc: ", rightAccAndVel[0]);
    	SmartDashboard.putNumber("rightVel: ", rightAccAndVel[1]);
    	SmartDashboard.putNumber("leftAcc: ", leftAccAndVel[0]);
    	SmartDashboard.putNumber("leftVel: ", leftAccAndVel[1]);
    	SmartDashboard.putNumber("dist", turnDistanceToGoal);
    	SmartDashboard.putNumber("headingAtPic", actualHeadingAtPictureTime);
    	SmartDashboard.putNumber("deltaAngleToBoiler", deltaAngleToBoiler);
    	SmartDashboard.putNumber("currentLeftVel", currentLeftVel);
    	SmartDashboard.putNumber("currentRightVel", currentRightVel);
    }
    
    private double[] findAccAndVel(int encoder0,int encoder1,int encoder2,double turnDistToGoal){
    	int histIndex = Robot.getHistoryIndex();
    	double deltaT = (Robot.getTime(histIndex)-Robot.getTime(histIndex-1))/1000000000;//seconds
    	double prevDeltaT = (Robot.getTime(histIndex-1)-Robot.getTime(histIndex-2))/1000000000;//seconds
    	int deltaLeftEncoders = encoder0-encoder1;
    	double currentLeftVelocity = (deltaLeftEncoders*Math.PI*4/12/256)/deltaT;
    	int prevDeltaLeftEncoders = encoder1-encoder2;
    	double prevLeftVelocity = (prevDeltaLeftEncoders*Math.PI*4/12/256)/prevDeltaT;
    	double currentLeftAcc = (currentLeftVelocity-prevLeftVelocity)/deltaT;
    	double thisLeftAcc;
    	double thisLeftVel;
    	if(turnDistToGoal*currentLeftVelocity<0){
    		//wrong way
    		thisLeftAcc = maxAcceleration*(currentLeftVelocity<0?1:-1);
    		thisLeftVel = currentLeftVelocity+(thisLeftAcc*0.02);
    	}else{
        	double timeToStop = Math.abs(currentLeftVelocity/maxAcceleration);
        	double distToStop = Math.abs(currentLeftVelocity*timeToStop)-(Math.abs(maxAcceleration*timeToStop*timeToStop)*0.5);
        	if(distToStop>=Math.abs(turnDistToGoal)){
        		thisLeftAcc = maxAcceleration*(currentLeftVelocity<0?1:-1);
        		thisLeftVel = currentLeftVelocity+(thisLeftAcc*0.02);
        	}else{
        		if(Math.abs(currentLeftVelocity)>=maxVelocity){
        			thisLeftAcc=0;
        			thisLeftVel=maxVelocity*(currentLeftVelocity<0?-1:1);
        		}else{
        			thisLeftAcc=(maxVelocity-Math.abs(currentLeftVelocity))/0.02*(currentLeftVelocity<0?-1:1);
        			if(Math.abs(thisLeftAcc)>maxAcceleration){
        				thisLeftAcc=maxAcceleration*(currentLeftVelocity<0?-1:1);
        			}
        			thisLeftVel=currentLeftVelocity+(thisLeftAcc*0.02);
        		}
        	}
    	}
    	double[] result = new double[3];
    	result[0] = thisLeftAcc;
    	result[1] = thisLeftVel;
    	result[2] = currentLeftVelocity;
    	return result;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putBoolean("aimIsFinished", Math.abs(centerline - desiredCenterline)<=tolerance&&Math.abs(currentLeftVel)<0.1&&Math.abs(currentRightVel)<0.1);
    	return Math.abs(centerline - desiredCenterline)<=tolerance&&Math.abs(currentLeftVel)<0.05&&Math.abs(currentRightVel)<0.05;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.getDriveBaseSimple().arcadeDrive(0, 0);
    	SmartDashboard.putString("end?", "yes");
		Robot.getDriveBaseSimple().setLeftMotor(0.0);
		Robot.getDriveBaseSimple().setRightMotor(0.0);
		Robot.getLEDSolenoid().LEDOff();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	SmartDashboard.putString("Interrupted?", "yes");
    	end();
    }
}
