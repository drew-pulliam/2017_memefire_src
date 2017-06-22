package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnTurretDegreesSimple extends Command {

	double m_motorLevel;
	double m_cutoff;
	double m_end;
	int m_startEncoder;
	double m_cutoffMotor;
    public TurnTurretDegreesSimple(double motorLevel,double cutoff,double end,double cutoffMotor) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getTurretSimple());
    	m_motorLevel = motorLevel;
    	m_cutoff = cutoff;
    	m_end = end;
    	m_cutoffMotor = cutoffMotor;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_startEncoder = Robot.getTurretSimple().getEncoder();
    	SmartDashboard.putNumber("startEncoderTurret", m_startEncoder);
    	SmartDashboard.putNumber("cutoffTurret", m_cutoff);
    	SmartDashboard.putNumber("endTurret", m_end);
    	Robot.getTurretSimple().setMotor(m_motorLevel);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(m_cutoff >= 0){
        	if(Robot.getTurretSimple().getEncoder() >= m_cutoff + m_startEncoder){
        		Robot.getTurretSimple().setMotor(m_cutoffMotor);
        	}
    	}else{
        	if(Robot.getTurretSimple().getEncoder() <= m_cutoff + m_startEncoder){
        		Robot.getTurretSimple().setMotor(m_cutoffMotor);
        	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Math.abs(Robot.getTurretSimple().getEncoder()) >= 475){
    		return true;
    	}
    	if(m_cutoff >= 0){
            return Robot.getTurretSimple().getEncoder() >= m_end + m_startEncoder;
    	}else{
            return Robot.getTurretSimple().getEncoder() <= m_end + m_startEncoder;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.getTurretSimple().setMotor(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
