
package org.usfirst.frc.team4587.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Bling;
import utility.GearCameraThread;
import utility.Gyro;
import utility.LogDataSource;
import utility.ValueLogger;
import utility.VisionCameraThread;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.usfirst.frc.team4587.robot.commands.Aim;
import org.usfirst.frc.team4587.robot.commands.AutoGearBayou;
import org.usfirst.frc.team4587.robot.commands.AutoGearCenter;
import org.usfirst.frc.team4587.robot.commands.AutoGearSide;
import org.usfirst.frc.team4587.robot.commands.AutoGearSide1;
import org.usfirst.frc.team4587.robot.commands.AutoMobility;
import org.usfirst.frc.team4587.robot.commands.AutonomousTRI;
import org.usfirst.frc.team4587.robot.subsystems.DriveBaseSimple;
import org.usfirst.frc.team4587.robot.subsystems.FlywheelPID;
import org.usfirst.frc.team4587.robot.subsystems.GearIntake;
import org.usfirst.frc.team4587.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements LogDataSource {

	private static Robot m_robot;
	public static Robot getInstance()
	{
		return m_robot;
	}
	private static OI m_oi;
	public static OI getOI()
	{
		return m_oi;
	}

	private static FlywheelPID m_flywheel;
	public static FlywheelPID getFlywheel()
	{
		return m_flywheel;
	}
	
	private static GearIntake m_gearIntake;
	public static GearIntake getGearIntake()
	{
		return m_gearIntake;
	}
	private static BallIntake m_ballIntake;
	public static BallIntake getBallIntake()
	{
		return m_ballIntake;
	}
	private static Feeder m_feeder;
	public static Feeder getFeeder()
	{
		return m_feeder;
	}
	private static HotDogs m_hotDogs;
	public static HotDogs getHotDogs()
	{
		return m_hotDogs;
	}
	private static HopperPiston m_hopperPiston;
	public static HopperPiston getHopperPiston(){
		return m_hopperPiston;
	}
	
	
	private static DriveBaseSimple m_driveBaseSimple;
	public static DriveBaseSimple getDriveBaseSimple()
	{
		return m_driveBaseSimple;
	}
	private static ClimbMotor m_climbMotor;
	public static ClimbMotor getClimbMotor()
	{
		return m_climbMotor;
	}
	private static GearCameraThread m_gearCameraThread;
	public static GearCameraThread getGearCameraThread(){
		return m_gearCameraThread;
	}
	private static PowerDistributionPanel m_PDP;
	public static PowerDistributionPanel getPDP(){
		return m_PDP;
	}
	private static LEDSolenoid m_LEDSolenoid;
	public static LEDSolenoid getLEDSolenoid(){
		return m_LEDSolenoid;
	}
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	private static ValueLogger  logger;
	private static SerialPort m_arduino;
	private FileOutputStream log;
	private static VisionCameraThread m_visionCameraThread;
	public static VisionCameraThread getVisionCameraThread(){
		return m_visionCameraThread;
	}
	static long[] times=null;
	public static long getTime(int index){
		return times[normalizeIndex(index)];
	}
	static double[] headings=null;
	public static double getHeading(int index){
		return headings[normalizeIndex(index)];
	}
	static int[] rightEncoders=null;
	public static int getRightEncoder(int index){
		return rightEncoders[normalizeIndex(index)];
	}
	static int[] leftEncoders=null;
	public static int getLeftEncoder(int index){
		return leftEncoders[normalizeIndex(index)];
	}
	static final int NUMBER_HIST=10000;
	static int historyIndex=0;
	public static int getHistoryIndex(){
		return historyIndex;
	}
	public static int normalizeIndex(int index){
		while(index>=NUMBER_HIST){
			index-=NUMBER_HIST;
		}while(index<0){
			index+=NUMBER_HIST;
		}
		return index;
	}
	public static int getIndexFromTime(long absoluteTime){
		long currentTime = getTime(historyIndex);
		if(absoluteTime>currentTime){
			return historyIndex;
		}else{
			int ticksIX0 = Math.round((currentTime - absoluteTime) / 20000000);
			long testTime = getTime(ticksIX0);
			while(testTime<absoluteTime){
				ticksIX0+=1;
				testTime = getTime(ticksIX0);
			}
			int ticksIX1 = ticksIX0;
			while(testTime>absoluteTime){
				ticksIX0-=1;
				testTime = getTime(ticksIX0);
			}
			ticksIX1 = ticksIX0+1;
			//IX0 is < than desired, IX1 is > than desired
			if(Math.abs(ticksIX0-absoluteTime)>Math.abs(ticksIX1-absoluteTime)){
				return ticksIX1;
			}else{
				return ticksIX0;
			}
		}
	}
	String lastActiveState=ValueLogger.DISABLED_PHASE;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("robotInit");
		m_robot = this;
		//m_turret = new TurretPID();
		
		Compressor compressor = new Compressor(0);
		//compressor.start();
    	m_ballIntake = new BallIntake();
		m_climbMotor = new ClimbMotor();;
		m_driveBaseSimple = new DriveBaseSimple();
		m_feeder = new Feeder();
		m_flywheel = new FlywheelPID();
    	m_gearIntake = new GearIntake();
		m_hotDogs = new HotDogs();
		m_hopperPiston = new HopperPiston();
		m_LEDSolenoid = new LEDSolenoid();
		//m_gearCameraThread = new GearCameraThread();
		
		m_PDP = new PowerDistributionPanel();
		
		Bling.initialize();
		/*try
		{
			//m_arduino = new SerialPort(9600, SerialPort.Port.kUSB);
			m_arduino = new SerialPort(9600, SerialPort.Port.kUSB);
		}
		catch(Exception e)
		{*/
			m_arduino = null;
			//System.out.println("arduino serial DEAD!");
		//}
		
		m_oi = new OI();
		logger = null;
        logger = new ValueLogger("/home/lvuser/dump",10);
        logger.registerDataSource(this);
        //logger.registerDataSource ( m_driveBase );
        logger.registerDataSource ( m_gearIntake );
        logger.registerDataSource ( m_driveBaseSimple );
        logger.registerDataSource(m_flywheel);
        //logger.registerDataSource(m_turret);
		/*chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);*/
        /*
    		try{
    			CameraServer.getInstance().startAutomaticCapture().setResolution(64, 48);
    		}catch(Exception e){
    			System.out.println(e);
    		}
    		*/
        times=new long[NUMBER_HIST];
        headings=new double[NUMBER_HIST];
        leftEncoders=new int[NUMBER_HIST];
        rightEncoders=new int[NUMBER_HIST];
        
	}
	static byte[] buffer = new byte [2];
	static int counter=0;
	public static void writeToArduino(byte mode)
	{
		if(m_arduino != null)
		{
			System.out.println("yep");
			buffer[0] = mode;
			buffer[1] = 10;
			m_arduino.write(buffer, 2);
			m_arduino.flush();
			counter++;
			SmartDashboard.putNumber("write to arduino count", counter);
			System.out.println(mode);
			SmartDashboard.putNumber("byte sent to arduino last:", mode);
		}
		else
		{
			System.out.println("nope");
		}
	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	void saveHistory(){
		times[historyIndex]=System.nanoTime();
		headings[historyIndex]=Gyro.getYaw();
		leftEncoders[historyIndex]=m_driveBaseSimple.getEncoderLeft();
		rightEncoders[historyIndex]=m_driveBaseSimple.getEncoderRight();
		historyIndex=(historyIndex+1)%NUMBER_HIST;
	}
	
	@Override
	public void disabledInit() {
		String prevState=lastActiveState;
		initializeNewPhase(ValueLogger.DISABLED_PHASE);
		Bling.sendData((byte)69);
		//m_turret.disable();
		if(m_visionCameraThread!=null){
			m_visionCameraThread.turnOff();
		}
		
		Robot.getFlywheel().setRunning(false);
		Robot.getFlywheel().disable();
		Robot.getFlywheel().setSetpoint(0.0);
		
		if(prevState.equals(ValueLogger.DISABLED_PHASE)==false){
			try{
				PrintStream p = new PrintStream("/home/lvuser/"+prevState+".csv");
				p.println("time,heading,right,left");
				for(int i = 0;i<historyIndex;i++){
					p.println(times[i]+","+headings[i]+","+rightEncoders[i]+","+leftEncoders[i]);
				}
				p.close();
			}catch(Exception e){}
		}
		//m_indexer.disable();
		//m_gearCameraThread.setRunning(false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		initializeNewPhase(ValueLogger.AUTONOMOUS_PHASE);
		//autonomousCommand = new AutoGearRight();
		//autonomousCommand = new AutoGearSimple("right");
		//autonomousCommand = new AutoGearSide("left");
		
		//autonomousCommand = new AutoGearSide1("right");
		//autonomousCommand = new AutoGearCenter();
		//autonomousCommand = new HopperAuto("blue");
		//autonomousCommand = new AutonomousTRI();
		m_visionCameraThread = new VisionCameraThread();
		m_visionCameraThread.start();
		autonomousCommand = new Aim();
		//autonomousCommand = new AutoMobility();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		
		historyIndex=0;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		long start = System.nanoTime();
        Scheduler.getInstance().run();
		m_driveBaseSimple.getValues();
       // if ( logger != null ) logger.logValues(start);
		//m_driveBase.getValues(); //put driveBase info on SmartDashboard
		saveHistory();
	}

	@Override
	public void teleopInit() {
		
		System.out.println("HI!!!!");
		initializeNewPhase(ValueLogger.TELEOP_PHASE);
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		m_visionCameraThread = new VisionCameraThread();
		m_visionCameraThread.start();
		
		
		System.out.println("init");
		//m_turret.enable();
		//m_flywheel.enable();
		//m_gearCameraThread.start();
		//m_indexer.enable();
		m_driveBaseSimple.resetEncoders();
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		try
		{
			log = new FileOutputStream("log.csv");
		}
		catch(Exception e)
		{
			
		}
		
		/*try{
			CameraServer.getInstance().startAutomaticCapture().setResolution(150, 150);
		}catch(Exception e){
			System.out.println(e);
		}*/
		//m_gearCameraThread.setRunning(true);
		//m_gearCameraThread.start();
		historyIndex=0;
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		long start = System.nanoTime();
		boolean on = true;
		SmartDashboard.putBoolean("visionThreadIsAlive", m_visionCameraThread.isAlive());
		SmartDashboard.putString("visionThreadState", m_visionCameraThread.getState().toString());
		Scheduler.getInstance().run();
		if ( logger != null ) logger.logValues(start);
		/*
		SmartDashboard.putNumber("Turret Encoder", m_turret.getEncoder());
		SmartDashboard.putNumber("Turret Degrees", m_turret.getDegrees());
		SmartDashboard.putNumber("Turret Heading", m_turret.getHeading());
		SmartDashboard.putNumber("Turret Setpoint", m_turret.getSetpoint());
		
		SmartDashboard.putNumber("turret encoder", m_turretSimple.getEncoder());
		*/
		//m_driveBase.getValues(); //put driveBase info on SmartDashboard
		m_driveBaseSimple.getValues();
		
		SmartDashboard.putNumber("Gyro Yaw",Gyro.getYaw());
		SmartDashboard.putBoolean("Is Running", m_flywheel.running());
		SmartDashboard.putNumber("Flywheel Encoder", m_flywheel.getEncoder().get());
		//SmartDashboard.putNumber("Turret Motor", m_turret.getTurretMotorActual());
		/*if (m_gearIntake.getGearIntakeSwitch() == false)
    	{
    		m_gearIntake.setGearIsLoaded(true);put back
    	}*/
		saveHistory();
		SmartDashboard.putNumber("PDP voltage", m_PDP.getVoltage());
		SmartDashboard.putNumber("PDP port 7", m_PDP.getCurrent(RobotMap.PDP_PORT_GEAR_INTAKE_MOTOR));
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	private void initializeNewPhase ( String whichPhase )
    {
		lastActiveState=whichPhase;
        if ( autonomousCommand != null ) {
            autonomousCommand.cancel();
            autonomousCommand = null;
        }
    	//if ( m_iAmARealRobot ) {
            //Robot.getDriveBase().initialize();
            //Robot.getIntake().initialize();
    	//}
        Gyro.reset();
        if ( logger != null ) logger.initializePhase(whichPhase);
    }
	
	public void gatherValues ( ValueLogger logger )
    {
		logger.logDoubleValue ( "Gyro Yaw", Gyro.getYaw() );
    	logger.logDoubleValue ( "Gyro Pitch", Gyro.getPitch() );
    	logger.logDoubleValue ( "Gyro Roll", Gyro.getRoll() );	
    	logger.logBooleanValue( "IMU_Connected", Gyro.IMU_Connected() );
    	logger.logBooleanValue( "IMU_IsCalibrating", Gyro.IMU_IsCalibrating() );
    }
}
