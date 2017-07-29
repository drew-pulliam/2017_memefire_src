package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import utility.Gyro;

/**
 *
 */
public class AutonomousTurnSimple extends Command {
	double m_startAngle;
	double m_desiredAngle;
	int count;
	double tolerance = 3;

    public AutonomousTurnSimple(double angleDegrees) {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	m_desiredAngle = angleDegrees;
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
    	m_startAngle = Gyro.getYaw();
    	count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	count++;
    	if(m_desiredAngle<0){
    		Robot.getDriveBaseSimple().setLeftMotor(-0.7);
    		Robot.getDriveBaseSimple().setRightMotor(0.7);
    	}else{
    		Robot.getDriveBaseSimple().setLeftMotor(0.7);
    		Robot.getDriveBaseSimple().setRightMotor(-0.7);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(m_startAngle+m_desiredAngle-Gyro.getYaw())<tolerance||count>100;
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
