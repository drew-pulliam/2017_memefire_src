package utility;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class Gyro 
{
		private static AHRS m_gyro;
		public enum PinType { DigitalIO, PWM, AnalogIn, AnalogOut };
	    
	    public static final int MAX_NAVX_MXP_DIGIO_PIN_NUMBER      = 9;
	    public static final int MAX_NAVX_MXP_ANALOGIN_PIN_NUMBER   = 3;
	    public static final int MAX_NAVX_MXP_ANALOGOUT_PIN_NUMBER  = 1;
	    public static final int NUM_ROBORIO_ONBOARD_DIGIO_PINS     = 10;
	    public static final int NUM_ROBORIO_ONBOARD_PWM_PINS       = 10;
	    public static final int NUM_ROBORIO_ONBOARD_ANALOGIN_PINS  = 4;
	    
	    public static int getChannelFromPin( PinType type, int io_pin_number ) 
	            throws IllegalArgumentException {
	     int roborio_channel = 0;
	     if ( io_pin_number < 0 ) {
	         throw new IllegalArgumentException("Error:  navX-MXP I/O Pin #");
	     }
	     switch ( type ) {
	     case DigitalIO:
	         if ( io_pin_number > MAX_NAVX_MXP_DIGIO_PIN_NUMBER ) {
	             throw new IllegalArgumentException("Error:  Invalid navX-MXP Digital I/O Pin #");
	         }
	         roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_DIGIO_PINS + 
	                           (io_pin_number > 3 ? 4 : 0);
	         break;
	     case PWM:
	         if ( io_pin_number > MAX_NAVX_MXP_DIGIO_PIN_NUMBER ) {
	             throw new IllegalArgumentException("Error:  Invalid navX-MXP Digital I/O Pin #");
	         }
	         roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_PWM_PINS;
	         break;
	     case AnalogIn:
	         if ( io_pin_number > MAX_NAVX_MXP_ANALOGIN_PIN_NUMBER ) {
	             throw new IllegalArgumentException("Error:  Invalid navX-MXP Analog Input Pin #");
	         }
	         roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_ANALOGIN_PINS;
	         break;
	     case AnalogOut:
	         if ( io_pin_number > MAX_NAVX_MXP_ANALOGOUT_PIN_NUMBER ) {
	             throw new IllegalArgumentException("Error:  Invalid navX-MXP Analog Output Pin #");
	         }
	         roborio_channel = io_pin_number;            
	         break;
	     }
	     return roborio_channel;
	     
	 }
	 private static void initializeGyro()
	 {
		 if (m_gyro == null)
		 {
			 m_gyro = new AHRS(SPI.Port.kMXP);
		 }
	 }
	 public static double getYaw()
	 {
		 initializeGyro();
		 return m_gyro.getYaw();
	 }
	 public static double getPitch()
	 {
		 initializeGyro();
		 return m_gyro.getPitch();
	 }
	 public static double getRoll()
	 {
		 initializeGyro();
		 return m_gyro.getRoll();
	 }
	 public static boolean IMU_Connected()
	 {
		 return m_gyro.isConnected();
	 }
	 public static boolean IMU_IsCalibrating()
	 {
		 return m_gyro.isCalibrating();
	 }
	 public static void reset()
	 {
		 initializeGyro();
		 m_gyro.reset();
	 }
}

