package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunIndexer extends Command {

	private double m_desiredVelocity;
	private double m_currentIndexerVelocity;
	private double m_velocityInRPMs;
	private double m_lastIndexerVelocity;
	private double m_lastIndexerEncoder;
    public RunIndexer() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getIndexer());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_desiredVelocity = 0.0;
    	m_lastIndexerEncoder = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.getIndexer().running())
    	{
    		m_desiredVelocity = SmartDashboard.getNumber("FlywheelVelocity", 0.0) / 60 / 50 * 256; // 60sec/min 50*20ms/sec 256dots/rev
    		SmartDashboard.putNumber("ConvertedFlywheelVelocity", m_desiredVelocity);
    		//m_currentIndexerVelocity = Robot.getIndexer().getVelocity(m_lastIndexerEncoder);
    		SmartDashboard.putNumber("Indexer Current Velocity", m_currentIndexerVelocity);
    		m_velocityInRPMs = m_currentIndexerVelocity * 60 * 50 / 256;
    		SmartDashboard.putNumber("Indexer Velocity RPMs", m_velocityInRPMs);
    		
    		double error = (m_desiredVelocity - m_currentIndexerVelocity) / 50;
    		Robot.getIndexer().setSetpoint(Robot.getIndexer().getSetpoint() + error);
        	m_lastIndexerVelocity = m_currentIndexerVelocity;
        	
        	SmartDashboard.putNumber("Indexer Setpoint", Robot.getIndexer().getSetpoint() + error);
    		SmartDashboard.putNumber("Indexer Error", error);
    	}
    	m_lastIndexerEncoder = Robot.getIndexer().getEncoder().get();
    	Robot.getIndexer().setLastEncoder(m_lastIndexerEncoder);
    	SmartDashboard.putNumber("Last Encoder Indexer", m_lastIndexerEncoder);
    	
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
