package org.usfirst.frc.team4587.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	public static final int PDP_PORT_GEAR_INTAKE_MOTOR = 11;
	public static final int GEAR_INTAKE_STALL_CURRENT = 20;

	public static final int MOTOR_RIGHT_DRIVETRAIN = 0;
	public static final int MOTOR_RIGHT_DRIVETRAIN_2 = 1;
	public static final int MOTOR_RIGHT_DRIVETRAIN_3 = 2;
	public static final int	MOTOR_LEFT_DRIVETRAIN = 3;
	public static final int MOTOR_LEFT_DRIVETRAIN_2 = 4;
	public static final int MOTOR_LEFT_DRIVETRAIN_3 = 5;
	public static final int MOTOR_CLIMB_1 = 14;
	public static final int MOTOR_CLIMB_2 = 15;
	public static final int MOTOR_FLYWHEEL_1 = 12; //11;
	public static final int MOTOR_FLYWHEEL_2 = 13; //15;
	public static final int MOTOR_GEAR_INTAKE = 6; //12;
	public static final int MOTOR_BALL_INTAKE = 10; //13;
	public static final int MOTOR_BALL_INTAKE_2 = 11; //16;
	public static final int MOTOR_HOTDOGS_1 = 8; //6;
	public static final int MOTOR_HOTDOGS_2 = 9; //17;
	public static final int MOTOR_FEEDER = 7;

	//
	public static final int SOLENOID_GEAR_INTAKE = 4;
	public static final int SOLENOID_BALL_INTAKE = 5;
	public static final int SOLENOID_HOPPER = 6;
	public static final int SOLENOID_LED_RING = 7;
	//
	public static final int ENCODER_FLYWHEEL_A = 5;
	public static final int ENCODER_FLYWHEEL_B = 4;
	public static final int ENCODER_RIGHT_DRIVE_A = 2;
    public static final int ENCODER_RIGHT_DRIVE_B = 3;
    public static final int ENCODER_LEFT_DRIVE_A = 0;
    public static final int ENCODER_LEFT_DRIVE_B = 1;

    public static final double DRIVE_SENSITIVIY_HIGH = 0.2;
    public static final double DRIVE_SENSITIVIY_LOW = 0.2;
	
	// If are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
