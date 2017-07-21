package org.usfirst.frc.team4587.robot.subsystems;

import java.io.FileOutputStream;

import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.DriveCheesyWithJoysticks;
import org.usfirst.frc.team4587.robot.commands.DriveSimpleWithJoysticks;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.LogDataSource;
import utility.ValueLogger;

/**
 *
 */
public class DriveBaseSimple extends Subsystem implements LogDataSource {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.\
	private SpeedController left0;//23
	private SpeedController left1;
	private SpeedController left2;
	private SpeedController right0;//01
	private SpeedController right1;
	private SpeedController right2;
	
	private RobotDrive drive0;
	private RobotDrive drive1;
	private RobotDrive drive2;
	public Encoder m_encoderRight, m_encoderLeft;
	
	public DriveBaseSimple()
	{
		left0 = new VictorSP(RobotMap.MOTOR_LEFT_DRIVETRAIN);
		left1 = new VictorSP(RobotMap.MOTOR_LEFT_DRIVETRAIN_2);
		left2 = new VictorSP(RobotMap.MOTOR_LEFT_DRIVETRAIN_3);
		right0 = new VictorSP(RobotMap.MOTOR_RIGHT_DRIVETRAIN);
		right1 = new VictorSP(RobotMap.MOTOR_RIGHT_DRIVETRAIN_2);
		right2 = new VictorSP(RobotMap.MOTOR_RIGHT_DRIVETRAIN_3);
		
		drive0 = new RobotDrive(left0, right0);
		drive1 = new RobotDrive(left1, right1);
		drive2 = new RobotDrive(left2, right2);
		
		m_encoderLeft = new Encoder(RobotMap.ENCODER_LEFT_DRIVE_A, 
				RobotMap.ENCODER_LEFT_DRIVE_B);

		m_encoderRight = new Encoder(RobotMap.ENCODER_RIGHT_DRIVE_A, 
				 RobotMap.ENCODER_RIGHT_DRIVE_B);
	}
	
	public void setLeftRightPower(double leftPower, double rightPower) {
	    left0.set(leftPower);
	    left1.set(leftPower);
	    left2.set(leftPower);
	    right0.set(rightPower);
	    right1.set(rightPower);
	    right2.set(rightPower);
	  }
	
	public void resetEncoders()
	{
		m_encoderLeft.reset();
		m_encoderRight.reset();
	}
	
	public void setLeftMotor(double speed)
	{
		left0.set(speed*-1);
		left1.set(speed*-1);
		left2.set(speed*-1);
	}
	
	public void setRightMotor(double speed)
	{
		right0.set(speed);
		right1.set(speed);
		right2.set(speed);
	}
	
	public int getEncoderLeft()
	{
		return m_encoderLeft.get();
	}
	
	public int getEncoderRight()
	{
		return m_encoderRight.get();
	}

	public void arcadeDrive(double drive, double turn)
	{
		drive0.arcadeDrive(drive*-1, turn);
		drive1.arcadeDrive(drive*-1, turn);
		drive2.arcadeDrive(drive*-1, turn);
	}
	
	public double straightDistanceTraveled(int startLeft, int startRight)
	{
		int deltaLeft = getEncoderLeft() - startLeft;
		int deltaRight = getEncoderRight() - startRight;
		double average = deltaLeft + deltaRight / 2.0;
		return average / 256 * Math.PI * 4;
	}
	
	public void getValues(){
    	SmartDashboard.putNumber("Left Motor Value", left0.get());
    	SmartDashboard.putNumber("Right Motor Value", right0.get());
    	SmartDashboard.putNumber("Left Encoder Value", getEncoderLeft());
    	SmartDashboard.putNumber("Right Encoder Value", getEncoderRight());
    	SmartDashboard.putNumber("Left Encoder Rate", m_encoderLeft.getRate());
    	SmartDashboard.putNumber("Right Encoder Rate", m_encoderRight.getRate());
    	/*SmartDashboard.putNumber("Right Encoder Delta Value", m_rightRingBuffer.getAverage());
    	SmartDashboard.putNumber("Left Encoder Delta Value", m_leftRingBuffer.getAverage());
    	SmartDashboard.putNumber("Delta Tolerance Value", m_tolerance);
    	SmartDashboard.putNumber("Delta Balance Value", m_balance);*/
    }
	
	public void gatherValues ( ValueLogger logger )
    {
    	logger.logDoubleValue ( "Left Motor Value",            left0.get() );
    	logger.logDoubleValue ( "Right Motor Value",           right0.get() );
    	logger.logIntValue    ( "Left Encoder Value",          getEncoderLeft() );
    	logger.logIntValue    ( "Right Encoder Value",         getEncoderRight() );
    	//logger.logDoubleValue ( "Distance Traveled",              straightDistanceTraveled() );
    }
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	//setDefaultCommand(new DriveSimpleWithJoysticks());
    	setDefaultCommand(new DriveCheesyWithJoysticks());
    }
}

