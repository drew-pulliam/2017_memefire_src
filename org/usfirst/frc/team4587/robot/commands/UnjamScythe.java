package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UnjamScythe extends Command {

	double m_scytheMotor;
	double m_shintakeMotor;
	int count;
    public UnjamScythe() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getScytheAndShintake());
    	m_scytheMotor = -1.0;
    	m_shintakeMotor = -1.0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.getScytheAndShintake().setShintakeMotor(m_shintakeMotor);
    	Robot.getScytheAndShintake().setScytheMotor(m_scytheMotor);
    	count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	count ++;
    	if(count > 2){
        	Robot.getScytheAndShintake().setScytheMotor(0.0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return count > 25;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.getScytheAndShintake().setShintakeMotor(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
