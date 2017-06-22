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
public class AutonomousTurnToAngleSimple extends Command {
	double m_startAngle;
	double m_desiredAngle;
	PIDController turnControl;
	myPIDSource m_myPIDSource;
	myPIDOutput m_myPIDOutput;
	int count;

    public AutonomousTurnToAngleSimple(double angleDegrees) {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	m_desiredAngle = angleDegrees;
    	m_myPIDSource = new myPIDSource();
    	m_myPIDOutput = new myPIDOutput();
    	turnControl = new PIDController(0.02, 0.001, 0.015, m_myPIDSource, m_myPIDOutput);
    	turnControl.setAbsoluteTolerance(12);
    	turnControl.setInputRange(-180, 180);
    	turnControl.setContinuous(true);
    }

    class myPIDOutput implements PIDOutput{

		@Override
		public void pidWrite(double output) {
			// TODO Auto-generated method stub
			Robot.getDriveBaseSimple().setLeftMotor(output);
			Robot.getDriveBaseSimple().setRightMotor(output*-1);
		}
    	
    }
    
    class myPIDSource implements PIDSource{

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			// TODO Auto-generated method stub
			return Gyro.getYaw();
		}
    	
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	turnControl.reset();
    	m_startAngle = Gyro.getYaw();
    	turnControl.setSetpoint(m_desiredAngle);
    	turnControl.enable();
    	count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*double direction;
    	if (Math.abs(Gyro.getYaw() - m_desiredAngle) < m_tolerance)
    	{
    		direction = 0;
    	}
    	else
    	if (m_startAngle < m_desiredAngle)
    	{
    		direction = 1;
    	}
    	else
    	{
    		direction = -1;
    	}
    	Robot.getDriveBaseSimple().arcadeDrive(0.0, m_speed * direction);*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	/*if (m_startAngle < m_desiredAngle)
    	{
    		return Gyro.getYaw() >= m_desiredAngle - m_tolerance;
    	}
    	else
    	{
    		return Gyro.getYaw() <= m_desiredAngle + m_tolerance;
    	}*/
    	if(count > 30)
    	{
        	return turnControl.onTarget();
    	}
    	else if(turnControl.onTarget())
    	{
    		count ++;
    		return false;
    	}
    	else 
    	{
    		count = 0;
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	turnControl.disable();
    	//Robot.getDriveBaseSimple().arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
