package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonomousDriveStraightDistance extends Command {
	int m_startRight;
	int m_startLeft;
	double m_distanceInches;
	double m_speed;
	double m_distanceTraveled;
	

    public AutonomousDriveStraightDistance(double distanceInches, double speed) {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	m_distanceInches = distanceInches;
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("initialize");
    	SmartDashboard.putNumber("dist In", m_distanceInches);
    	m_startRight = Robot.getDriveBaseSimple().getEncoderRight();
    	m_startLeft = Robot.getDriveBaseSimple().getEncoderLeft();
    	Robot.getDriveBaseSimple().arcadeDrive(m_speed, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("exec");
    	m_distanceTraveled = Robot.getDriveBaseSimple().straightDistanceTraveled(m_startLeft, m_startRight);
    	Robot.getDriveBaseSimple().arcadeDrive(m_speed, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("isfin");
    	SmartDashboard.putNumber("distancetraveled", m_distanceTraveled);
    	SmartDashboard.putNumber("startRight", m_startRight);
    	if(m_distanceInches > 0)
    	{
    		return Robot.getDriveBaseSimple().straightDistanceTraveled(m_startLeft, m_startRight) >= m_distanceInches;
    	}
    	else
    	{
    		return Robot.getDriveBaseSimple().straightDistanceTraveled(m_startLeft, m_startRight) <= m_distanceInches;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {

    	System.out.println("end");
    	Robot.getDriveBaseSimple().arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
