package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurretSimpleWithJoysticks extends Command {

    public TurretSimpleWithJoysticks() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getTurretSimple());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double turnStick = Robot.getOI().getTurn2() *0.2;//* Math.abs(Robot.getOI().getTurn2());
    	double turnStick2 = Robot.getOI().getTurn3() *0.6;//* Math.abs(Robot.getOI().getTurn2());
    	if(Robot.getTurretSimple().getEncoder() >= 475){
    		if(turnStick >= 0){
    			turnStick = 0;
    		}
    		if(turnStick2 >=0){
    			turnStick2 =0;
    		}
    	}
    	if(Robot.getTurretSimple().getEncoder() <= -475){
    		if(turnStick <= 0){
    			turnStick = 0;
    		}
    		if(turnStick2 <=0){
    			turnStick2 =0;
    		}
    	}
    	if(Math.abs(turnStick/0.2)>Math.abs(turnStick2/0.6)){
        	Robot.getTurretSimple().setMotor(turnStick);
    	}else{
    		Robot.getTurretSimple().setMotor(turnStick2);
    	}
    	SmartDashboard.putNumber("TurretSimple Encoder", Robot.getTurretSimple().getEncoder());
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
    }
}
