package org.usfirst.frc.team4587.robot.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Gyro;
import utility.Path;
import utility.TextFileDeserializer;
import utility.Trajectory;

/**
 *
 */
public class FollowChezyPath extends Command {

	BufferedReader m_bufferedReader;
	boolean quit;
	int m_startEncoderLeft;
	int m_startEncoderRight;
	double m_startAngle;
	double m_startTime;
	double Ka = 0.01;//0.1
	double Kv = 0.4/43;
	double Kp = 0.005;
	double Kg = 0.015;
	Path m_path;
	FileWriter m_logWriter;
	String m_namePath;
	String filename;
	boolean m_backwards;
	boolean m_reverseLeftRight;
	double m_finalPositionRight;
	double m_finalPositionLeft;
	double m_gyroMultiplier;
    public FollowChezyPath(String namePath, boolean backwards, boolean reverseLeftRight,double gyroMultiplier, double degrees) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	m_namePath = namePath;
    	m_backwards = backwards;
    	m_reverseLeftRight = reverseLeftRight;
    	filename = "/home/lvuser/" + m_namePath + ".txt";
    	m_gyroMultiplier = gyroMultiplier;
    	m_startAngle = degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	quit = false;
    	try{
    		m_bufferedReader = new BufferedReader(new FileReader(filename));
    		StringBuilder sb = new StringBuilder();
    		String line;
    		while((line = m_bufferedReader.readLine()) != null)
    		{
    			sb.append(line).append("\n");
    		}
    		m_bufferedReader.close();
    		m_path = (new TextFileDeserializer()).deserialize(sb.toString());
    	}catch(Exception e){
    		System.out.println(e);
    	}

		try {
			m_logWriter = new FileWriter("/home/lvuser/" + m_namePath +"Log.csv", false);
			m_logWriter.write("aLeft,vLeft,xLeft,aRight,vRight,xRight,desiredAngle,currentAngle,realLeftEncoder,realRightEncoder,leftMotorLevel,rightMotorLevel,System.nanoTime()" + "\n");
		} catch ( IOException e ) {
			System.out.println(e);
			m_logWriter = null;
		}
    	m_startEncoderLeft = Robot.getDriveBaseSimple().getEncoderLeft();
    	m_startEncoderRight = Robot.getDriveBaseSimple().getEncoderRight();
    	m_startTime = System.nanoTime();
    	
    	m_finalPositionLeft = m_path.getLeftWheelTrajectory().getSegment(m_path.getLeftWheelTrajectory().getNumSegments() - 1).pos * 12 / 0.049 *-1;
    	m_finalPositionRight = m_path.getRightWheelTrajectory().getSegment(m_path.getRightWheelTrajectory().getNumSegments() - 1).pos * 12 / 0.049 *-1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = System.nanoTime();
    	double dt = (time - m_startTime) / 1000000;
    	int step0 = (int)(dt / 20);
    	int step1 = step0 + 1;
    	double offset = dt - 20 * step0;
    	
    	double aLeft;
		double vLeft;
		double xLeft;
		double aRight;
		double vRight;
		double xRight;
    	
    	if(step1 >= m_path.getLeftWheelTrajectory().getNumSegments())
    	{
    		quit = true;
    	}
    	/*else if(m_backwards && m_finalPositionRight >= Robot.getDriveBaseSimple().getEncoderRight() && m_finalPositionLeft >= Robot.getDriveBaseSimple().getEncoderLeft()){
    		quit = true;
    	}
    	else if(m_backwards == false && m_finalPositionRight <= Robot.getDriveBaseSimple().getEncoderRight() && m_finalPositionLeft <= Robot.getDriveBaseSimple().getEncoderLeft()){
    		quit = true;
    	}*/
    	else
        	{
	    		Trajectory.Segment left0;
	        	Trajectory.Segment right0;
	    		Trajectory.Segment left1;
	        	Trajectory.Segment right1;
    			if(m_reverseLeftRight == false)
    			{
            		left0 = m_path.getLeftWheelTrajectory().getSegment(step0);
                	right0 = m_path.getRightWheelTrajectory().getSegment(step0);
            		left1 = m_path.getLeftWheelTrajectory().getSegment(step1);
                	right1 = m_path.getRightWheelTrajectory().getSegment(step1);
    			}
    			else //swap left and right
    			{
            		right0 = m_path.getLeftWheelTrajectory().getSegment(step0);
                	left0 = m_path.getRightWheelTrajectory().getSegment(step0);
            		right1 = m_path.getLeftWheelTrajectory().getSegment(step1);
                	left1 = m_path.getRightWheelTrajectory().getSegment(step1);
    			}
    			
            	if(m_backwards == true) //multiply all by -1
            	{
	        		aLeft = (left0.acc + ((offset / 20) * (left1.acc - left0.acc))) * 12 / 0.049 / 1000 * 20 / 1000 * 20 *-1;
	        		vLeft = (left0.vel + ((offset / 20) * (left1.vel - left0.vel))) * 12 / 0.049 / 1000 * 20 *-1;
	        		xLeft = (left0.pos + ((offset / 20) * (left1.pos - left0.pos))) * 12 / 0.049 *-1;
	        		aRight = (right0.acc + ((offset / 20) * (right1.acc - right0.acc))) * 12 / 0.049 / 1000 * 20 / 1000 * 20 *-1;
	        		vRight = (right0.vel + ((offset / 20) * (right1.vel - right0.vel))) * 12 / 0.049 / 1000 * 20 *-1;
	        		xRight = (right0.pos + ((offset / 20) * (right1.pos - right0.pos))) * 12 / 0.049 *-1;
            	}
            	else
            	{
            		aLeft = (left0.acc + ((offset / 20) * (left1.acc - left0.acc))) * 12 / 0.049 / 1000 * 20 / 1000 * 20;
	        		vLeft = (left0.vel + ((offset / 20) * (left1.vel - left0.vel))) * 12 / 0.049 / 1000 * 20;
	        		xLeft = (left0.pos + ((offset / 20) * (left1.pos - left0.pos))) * 12 / 0.049;
	        		aRight = (right0.acc + ((offset / 20) * (right1.acc - right0.acc))) * 12 / 0.049 / 1000 * 20 / 1000 * 20;
	        		vRight = (right0.vel + ((offset / 20) * (right1.vel - right0.vel))) * 12 / 0.049 / 1000 * 20;
	        		xRight = (right0.pos + ((offset / 20) * (right1.pos - right0.pos))) * 12 / 0.049;
            	}

        		double desiredAngle = right0.heading * 180 / Math.PI * m_gyroMultiplier; //* -1;
        		double currentAngle = Gyro.getYaw();
        		int realLeftEncoder = Robot.getDriveBaseSimple().getEncoderLeft();
        		int realRightEncoder = Robot.getDriveBaseSimple().getEncoderRight();
        		desiredAngle += m_startAngle;
        		while(desiredAngle > 180)
        		{
        			desiredAngle -= 360;
        		}
        		while(desiredAngle < -180)
        		{
        			desiredAngle += 360;
        		}
        		xLeft += m_startEncoderLeft;
        		xRight += m_startEncoderRight;
        		double leftMotorLevel = Ka * aLeft + Kv * vLeft - Kp * (realLeftEncoder - xLeft) - Kg * (currentAngle - desiredAngle);
        		double rightMotorLevel = Ka * aRight + Kv * vRight - Kp * (realRightEncoder - xRight) + Kg * (currentAngle - desiredAngle);
        		
        		Robot.getDriveBaseSimple().setLeftMotor(leftMotorLevel);
        		Robot.getDriveBaseSimple().setRightMotor(rightMotorLevel);
        		SmartDashboard.putNumber("left motor set to: ", leftMotorLevel);
        		SmartDashboard.putNumber("right motor set to: ", rightMotorLevel);
            	
        		if(m_logWriter != null)
        		{
        			try{
        				m_logWriter.write(aLeft + "," + vLeft + "," + xLeft + "," + aRight + "," + vRight + "," + xRight + "," + desiredAngle + "," + currentAngle + "," + realLeftEncoder + "," + realRightEncoder + "," + leftMotorLevel + "," + rightMotorLevel + "," + time + "\n");
        			}catch(Exception e){
        				
        			}
        		}
        	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return quit;
    }

    // Called once after isFinished returns true
    protected void end() {
    	try
    	{
    		m_logWriter.close();
    	}
    	catch(Exception e)
    	{
    		
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
