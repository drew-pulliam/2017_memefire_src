package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Aim extends Command {

	private double m_centerline;
	private double m_lastCenterline;
    public Aim() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getTurret());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_lastCenterline = -5000;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*if(Robot.getTurret().aiming())
    	{
	    	m_centerline = SmartDashboard.getNumber("pi.centerline", -1);
	    	if (m_centerline > 0)
	    	{
	    		if(Math.abs(m_centerline - 320) > 20)
	    		{
	    			Robot.writeToArduino((byte)65);
	    			SmartDashboard.putNumber("byte sent to arduino", 65);
	    			SmartDashboard.putNumber("last centerline", m_lastCenterline);
	    			if(m_lastCenterline != m_centerline)
	    			{
		    			double error = (m_centerline - 320) / 16.0;
		    			Robot.getTurret().setSetpoint(Robot.getTurret().getSetpoint() + error);
		    			//Robot.getTurret().setSetpoint(120);
		    			m_lastCenterline = m_centerline;
		    			SmartDashboard.putNumber("Desired Setpoint", Robot.getTurret().getSetpoint() + error);
	    			}
	    		}
	    		else
	    		{
	    			Robot.writeToArduino((byte)64);
	    			SmartDashboard.putNumber("byte sent to arduino", 64);
	    		}
	    	}
    	}*/
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
