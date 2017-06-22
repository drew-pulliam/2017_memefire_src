package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnTurretDegrees extends Command {
	
	private double m_degreesToTurn;
	private double m_startDegrees;
	private double m_degreesLeftToTurn;
	private int m_count;

    public TurnTurretDegrees(int degrees) {
    	m_degreesToTurn = degrees;
    	requires(Robot.getTurret());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	m_degreesLeftToTurn = m_degreesToTurn;
    	m_startDegrees = Robot.getTurret().getDegrees();
    	//Robot.getTurret().setTurretMotorTarget(motorLevel);
    	//SmartDashboard.putNumber("turret motor inside command", Robot.getTurret().getTurretMotorActual());
    	//m_startEncoders = 10;
    	m_count = 0;
    	Robot.getTurret().setSetpointRelative(m_degreesToTurn);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	/*double newDegreesLeftToTurn = 0;
    	if (m_degreesToTurn < 0)
        {
        	newDegreesLeftToTurn = m_degreesToTurn + (m_startDegrees - Robot.getTurret().getDegrees());
        }
    	else
    	{
    		newDegreesLeftToTurn = m_degreesToTurn - (Robot.getTurret().getDegrees() - m_startDegrees);
    	}
    	
    	
    	if (Math.abs(newDegreesLeftToTurn - m_degreesLeftToTurn) <= 0.01)
    	{
    		if (m_degreesToTurn < 0)
    		{
            	Robot.getTurret().setTurretMotorTarget(Robot.getTurret().whatMotorLevel(newDegreesLeftToTurn, m_degreesLeftToTurn) - 0.05);
    		}
    		else
    		{
            	Robot.getTurret().setTurretMotorTarget(Robot.getTurret().whatMotorLevel(newDegreesLeftToTurn, m_degreesLeftToTurn) + 0.05);
    		}
    	}
    	else
    	{
        	Robot.getTurret().setTurretMotorTarget(Robot.getTurret().whatMotorLevel(newDegreesLeftToTurn, m_degreesLeftToTurn));
    	}
    	
    	Robot.getTurret().updateTurretMotor();
    	m_count += 1;
    	m_degreesLeftToTurn = newDegreesLeftToTurn;
    	SmartDashboard.putNumber("turret count", m_count);
    	SmartDashboard.putNumber("What motor level", Robot.getTurret().getTurretMotorActual());
    	
    	SmartDashboard.putNumber("degrees left to turn", m_degreesLeftToTurn);*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        /*if (m_degreesToTurn < 0)
        {
        	return m_degreesToTurn >= Robot.getTurret().getDegrees() - m_startDegrees;
        }
        else if (m_degreesToTurn > 0)
        {
        	return m_degreesToTurn <= Robot.getTurret().getDegrees() - m_startDegrees;        	
        }
        else
        {
        	return true;
        }*/
    	return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.getTurret().setTurretMotorTarget(0.0);
    	//Robot.getTurret().updateTurretMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
