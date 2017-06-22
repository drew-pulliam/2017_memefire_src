package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetScytheAndShintake extends Command {

	double m_scytheMotor;
	double m_shintakeMotor;
	int count;
	int m_delay;
    public SetScytheAndShintake(double scytheMotor,double shintakeMotor, int delay) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getScytheAndShintake());
    	m_scytheMotor = scytheMotor;
    	m_shintakeMotor = shintakeMotor;
    	m_delay = delay;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.getScytheAndShintake().setShintakeMotor(m_shintakeMotor);
    	count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	count ++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return count > m_delay;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.getScytheAndShintake().setScytheMotor(m_scytheMotor);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
