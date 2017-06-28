package org.usfirst.frc.team4587.robot;

import org.usfirst.frc.team4587.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Gyro;
import utility.JoyButton;
import utility.LogDataSource;
import utility.ValueLogger;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements LogDataSource {
	Joystick  stick1;
	Button	  buttonA1, buttonB1, buttonX1, buttonY1, leftBumper1, rightBumper1;
	JoyButton leftTrigger1, rightTrigger1;
	Joystick  stick2;
	Button	  buttonA2, buttonB2, buttonX2, buttonY2, leftBumper2, rightBumper2;
	JoyButton leftTrigger2, rightTrigger2;
	
    public OI()
    {
    	stick1			= new Joystick(1);
    	buttonA1		= new JoystickButton(stick1, 1);
    	buttonB1		= new JoystickButton(stick1, 2);
    	buttonX1		= new JoystickButton(stick1, 3);
    	buttonY1		= new JoystickButton(stick1, 4);
    	leftBumper1 	= new JoystickButton(stick1, 5);
    	leftTrigger1	= new JoyButton(stick1, JoyButton.JoyDir.DOWN, 2);
    	rightBumper1	= new JoystickButton(stick1, 6);
    	rightTrigger1	= new JoyButton(stick1, JoyButton.JoyDir.DOWN, 3);
    	
    	stick2			= new Joystick(2);
    	buttonA2		= new JoystickButton(stick2, 1);
    	buttonB2		= new JoystickButton(stick2, 2);
    	buttonX2		= new JoystickButton(stick2, 3);
    	buttonY2		= new JoystickButton(stick2, 4);
    	leftBumper2 	= new JoystickButton(stick2, 5);
    	leftTrigger2	= new JoyButton(stick2, JoyButton.JoyDir.DOWN, 2);
    	rightBumper2	= new JoystickButton(stick2, 6);
    	rightTrigger2	= new JoyButton(stick2, JoyButton.JoyDir.DOWN, 3);
/*
    	buttonY1.whenPressed(new SetScytheAndShintake(0.7,0.0,0));
    	buttonA1.whenPressed(new SetScytheAndShintake(0.7,1.0,25));
    	buttonB1.whenPressed(new SetScytheAndShintake(0.0,0.0,0));
    	buttonX1.whenPressed(new SetScytheAndShintake(0.0,1.0,0));

    	//buttonX1.whenPressed(new TestFlywheelDecrease());
    	//buttonY1.whenPressed(new TestFlywheelIncrease());
    	leftBumper1.whenPressed(new SetFlywheel(2900));
    	rightBumper1.whenPressed(new ToggleFlywheelRunning());
    	*/
    	/*buttonX1.whenPressed(new SetFlywheelMotorsSimple(0.5));
    	buttonY1.whenPressed(new SetFlywheelMotorsSimple(0.0));
    	leftBumper1.whenPressed(new SetFlywheelMotorsSimple(1.0));
    	rightBumper1.whenPressed(new SetFlywheelMotorsSimple(0.75));*/
    	//buttonA1.whenPressed(new testTurretIncrease());
    	//buttonX1.whenPressed(new ToggleAimMode());
    	//buttonB1.whenPressed(new testTurretDecrease());
    	//buttonY1.whenPressed(new testTurretSetSetpoint());
    	/*leftBumper1.whenPressed(new ToggleGearIntakeMotors());
    	rightBumper1.whenPressed(new EjectGear());
    	buttonA1.whenPressed(new ChangeLEDMode((byte)64));
    	buttonY1.whenPressed(new ChangeLEDMode((byte)65));
    	buttonB1.whenPressed(new ChangeLEDMode((byte)66));
    	*/
    	//buttonA1.whenPressed(new PlanPathTest());
    	//buttonA1.whenPressed(new AutonomousMotionTesting(0.5));
    	/*
    	buttonX1.whenPressed(new AimFixedShooter());
		buttonB1.whenPressed(new DriveSimpleWithJoysticks());
		buttonY1.whenPressed(new AutonomousTurnToAngleSimple(135, 0.6, 2));
		leftBumper1.whenPressed(new PlanPathTest());
		rightBumper1.whenPressed(new FollowPath());
		*/
    	/*buttonA1.whenPressed(new TestFlywheelIncrease());
    	buttonB1.whenPressed(new TestFlywheelDecrease());
    	buttonX1.whenPressed(new ToggleFlywheelRunning());
    	buttonY1.whenPressed(new SetFlywheel(3000, 0.8));*/
    	
    	//buttonX1.whenPressed(new ClimbMotorStart());
    	//buttonY1.whenPressed(new ClimbMotorStop());
    	//buttonB1.whenPressed(new DriveSimpleWithJoysticks());
    	/*
    	buttonA1.whenPressed(new AutoGearIntakeMotors());
    	buttonB1.whenPressed(new ToggleGearIntakeMotors());
    	buttonX1.whenPressed(new BallIntakeOff());
    	buttonY1.whenPressed(new ClimbMotorToggle());
    	rightBumper1.whenPressed(new EjectGear());
    	leftBumper1.whenPressed(new ToggleGearIntakeUpDown());
    	leftTrigger1.whenPressed(new StartGearCamera());
    	
    	buttonB2.whenPressed(new BallIntakeOff());
    	buttonX2.whenPressed(new ToggleFlywheelRunning(true,2900));// 3040, 3070
    	buttonY2.whenPressed(new ToggleFlywheelRunning(false,0));
    	leftBumper2.whenPressed(new BallIntakeOn());
    	//rightBumper2.whenPressed(new ToggleHopperInOut());
    	leftTrigger2.whenPressed(new BallIntakeUnJam());
    	rightTrigger2.whenPressed(new BallIntakeOut());
    	*/
    	buttonA1.whenPressed(new AutoGearIntakeMotors());
    	buttonB1.whenPressed(new GearIntakeIdle());
    	buttonX1.whenPressed(new BallIntakeOn());
    	buttonY1.whenPressed(new BallIntakeOff());
    	rightBumper1.whenPressed(new EjectGear());
    	leftBumper1.whenPressed(new ClimbMotorToggle());
    	rightTrigger1.whenPressed(new ToggleGearIntakeUpDown());
    	leftTrigger1.whileHeld(new BallIntakeOut());
    	/*
    	buttonA1.whenPressed(new AutoGearSide1("left"));
    	buttonB1.whenPressed(new AutoGearSide1("right"));
    	buttonX1.whenPressed(new FollowChezyPath("HopperAfterGearPath0",true,false,1,0));
    	buttonY1.whenPressed(new FollowChezyPath("Turn75Path",false,false,1,0));
    	*/
    	
    	/*
    	leftBumper1.whenPressed(new ToggleGearIntakeUpDown());
    	buttonA1.whenPressed(new DriveSimpleWithJoysticks());
    	buttonB1.whenPressed(new DriveCheesyWithJoysticks());
    	buttonX1.whenPressed(new FollowChezyPath("hopperPathWorlds",false,false,1,Gyro.getYaw()));
    	buttonY1.whenPressed(new DriveSet(0.5,-0.5));
    	*/
    	//buttonA1.whenPressed(new TurnTurretDegreesSimple(0.3,350,393,0.2));
    	//buttonB1.whenPressed(new TurnTurretDegreesSimple(-0.3,-350,-393,-0.2));
    	
    	//buttonA1.whenPressed(new HopperAutoSimple("blue"));
    	//buttonB1.whenPressed(new HopperAutoSimple("red"));
    	//rightBumper2.whenPressed(new BallIntakeOff());
    	
/*
    	buttonA1.whenPressed(new FollowChezyPath("sideGearPathWorlds",false,true,-1,Gyro.getYaw()));
    	buttonB1.whenPressed(new FollowChezyPath("sideGearPathWorlds",false,false,1,Gyro.getYaw()));
    	buttonX1.whenPressed(new AutoGearSide("right"));
    	*/
    	//buttonA1.whenPressed(new FollowChezyPath("hopperPathWorlds",false,true,-1,Gyro.getYaw())); true = -1 = left turn
    	//buttonB1.whenPressed(new FollowChezyPath("hopperPathWorlds",false,false,1,Gyro.getYaw())); false = 1 = right turn
    	//buttonA2.whenPressed(new SetScytheAndShintake(0.4,1.0,25));
    	//buttonB2.whenPressed(new SetScytheAndShintake(0.0,0.0,0));
    	//buttonA2.whenPressed(new SetScytheAndShintake(0.0,1.0,0));
    	//leftBumper2.whenPressed(new UnjamScythe());
    	
    	//rightBumper2.whenPressed(new TestFlywheelIncrease());
    	//rightBumper2.whenPressed(new SetScytheAndShintake(-1.0,-1.0,0));
    	
    	
    	/*leftBumper1.whenPressed(new ToggleGearIntakeUpDown());
    	rightBumper1.whenPressed(new EjectGear());
    	buttonA1.whenPressed(new FollowChezyPath("CenterGearPath", false, false));
    	//buttonB1.whenPressed(new AutoGearCenter());
    	buttonX1.whenPressed(new AutoGearSide("right"));
    	buttonY1.whenPressed(new AutonomousTurnToAngleSimple(-57));*/
    	//buttonA1.whenPressed(new AutoGearSimple("right"));
    	//buttonY1.whenPressed(new AimGearDriveNoPi());
    	//buttonB1.whenPressed(new AutoGearBayou("right"));
    	
    	//buttonA1.whenPressed(new AutoGearTest());
    	//buttonA1.whenPressed(new RunGearCameraThread(true));
    	//buttonB1.whenPressed(new RunGearCameraThread(false));
    	//buttonX1.whenPressed(new AimGearNoPi());
    	//buttonX1.whenPressed(new AutonomousTurnToAngleSimple(-60));
    	/*buttonA1.whenPressed(new AutoGearSimple("right"));
    	buttonY1.whenPressed(new RunGearCameraThread());
    	buttonX1.whenPressed(new AimGearDriveNoPi());
    	buttonB1.whenPressed(new AutonomousDriveStraightDistance(50,0.7));*/
    	/*buttonA1.whenPressed(new ChangeLEDMode((byte)65));
    	buttonB1.whenPressed(new ChangeLEDMode((byte)66));
    	buttonX1.whenPressed(new ChangeLEDMode((byte)67));
    	buttonY1.whenPressed(new ChangeLEDMode((byte)68));
    	leftBumper1.whenPressed(new ChangeLEDMode((byte)69));
    	rightBumper1.whenPressed(new ChangeLEDMode((byte)70));
    	*/
    	/*buttonA1.whenPressed(new AimGear());
    	buttonX1.whenPressed(new AimGearDrive());
    	buttonB1.whenPressed(new DriveSimpleWithJoysticks());
    	buttonY1.whenPressed(new AutonomousDriveStraightDistance(1000, 0.7));*/
    	
    	//  D R I V E R   C O N T R O L L E R

    	/*if ( Robot.iAmARealRobot()) {
    		buttonB1.whenPressed(new DriveWithJoysticks());
    		buttonA1.whenPressed(new RunCameraThread());
    		buttonX1.whenPressed(new AutomaticAim(0, 0.6));
	    	buttonY1.whenPressed(new ToggleFlashlight());
	    	leftBumper1.whenPressed(new ToggleArmPiston());
	    	leftTrigger1.whileHeld(new LowerAndHoldArm());
	    	rightBumper1.whenPressed(new LowShot());
    		rightTrigger1.whenPressed(new HighShot());
    	}
    	else 
    	{
    		buttonA1.whenPressed(new RunCameraThread());
    	}*/

    	//  I N T A K E   C O N T R O L L E R

    	/*if ( Robot.iAmARealRobot()) {
	    	//buttonA2.whenPressed(new AutoIntakeBall());
	    	buttonA2.whenPressed(new ToggleFlashlight());
	    	buttonB2.whenPressed(new StopIntakeMotors());
	    	buttonX2.whenPressed(new StartIntakeMotors(Parameters.getDouble("Intake Motor Eject Speed", -1.0)));
	    	buttonY2.whenPressed(new StartIntakeMotors(Parameters.getDouble("Intake Motor Input Speed", 1.0)));
	    	rightBumper2.whenPressed(new ToggleIntakePiston());
	    	leftBumper2.whenPressed(new PulseLowGoalSolenoid(1000));
    	}*/
    }
    
    public double getTurn()
    {
    	return stick1.getRawAxis(4);
    }
    public double getTurn2()
    {
    	return stick2.getRawAxis(4);
    }
    public double getTurn3()
    {
    	return stick2.getRawAxis(0);
    }
    public double getDrive()
    {
    	return stick1.getRawAxis(1) * -1;
    }
    
    public void rumble( float value )
    {
    	stick1.setRumble(Joystick.RumbleType.kRightRumble, value);
    }
    
    public void gatherValues ( ValueLogger logger )
    {
    	logger.logDoubleValue("Driving Joystick Value", getDrive());
    	logger.logDoubleValue("Turning Joystick Value", getTurn());
    }
}
