package utility;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoProperty;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearCameraThread extends Thread{
	  String oldMode = "startUp";
	  String mode = "HumanVision";
	  MjpegServer cameraStream;
	  UsbCamera camera2;
	  int streamCameraPort = 5805;
	  CvSink imageSink;
	  MjpegServer cvStream;
	  GripPipeline gp;
	  Mat inputImage;
	  Mat hsv;
	  CvSource imageSource;
	  
	  private double gearCenterline;
	  private double gearArea;
	  public double getGearCenterline()
	  {
		  return gearCenterline;
	  }
	  public double getGearArea()
	  {
		  return gearArea;
	  }
	  
	  private boolean running = false;
	  public boolean isRunning()
	  {
		  return running;
	  }
	  public void setRunning(boolean x)
	  {
		  running = x;
	  }
	  public void setMode(String xmode){
		  mode = xmode;
	  }
	  
	  public void run()
	  {
		  running = true; // This change makes it so we don't have to remember to call setRunning() before startinh the thread.
		  while(running)
		  {
			  //mode = SmartDashboard.getString("GearCameraMode", "HumanVision");
			  if (oldMode.equals("startUp"))
			  {
				    cameraStream = new MjpegServer("MJPEG", streamCameraPort);
				    camera2 = setUsbCamera2(0,cameraStream);
				    camera2.setResolution(320,240);
				    imageSink = new CvSink("CV Image Grabber");
				    imageSink.setSource(camera2);
				    imageSource = new CvSource("CV Image Source", VideoMode.PixelFormat.kMJPEG, 320, 240, 30);
				    cvStream = new MjpegServer("CV Image Stream", 1188);
				    cvStream.setSource(imageSource);
				    gp = new GripPipeline();
				    inputImage = new Mat();
				    hsv = new Mat();
			  }
			  if(mode.equals("HumanVision"))
			  {
				  if(oldMode.equals("HumanVision") == false)
				  {
					    camera2.setWhiteBalanceAuto();
					    camera2.setExposureManual(70);
				  }
				  try
				  {
					  currentThread().sleep(100);
				  }catch(Exception e)
				  {
					  
				  }
			  }
			  else
			  {
				  SmartDashboard.putString("pi.hi", "hi!");
				  if(oldMode.equals("ComputerVision") == false)
				  {
					  VideoProperty wbm = camera2.getProperty("white_balance_temperature");
					  camera2.setWhiteBalanceManual(wbm.getMin());
					  camera2.setExposureManual(0);
				  }
					gearCenterline= -1;
				long start = System.nanoTime();
				long frameTime = imageSink.grabFrame(inputImage);
				if (frameTime != 0) 
				{
					//Imgproc.cvtColor(inputImage, hsv, Imgproc.COLOR_BGR2HSV);
					//Core.inRange(hsv, new Scalar(70.0, 200.0, 70),
					//	new Scalar(100.0, 255.0, 120), hsv);
					gp.processGear(inputImage);
					//imageSource.putFrame(gp.hsvThresholdOutput());
					imageSource.putFrame(inputImage);
					SmartDashboard.putNumber("piGear.centerline", gp.centerline);
					SmartDashboard.putNumber("piGear.height", gp.height);
					SmartDashboard.putNumber("piGear.area", gp.area);
					SmartDashboard.putNumber("piGear.time", (System.nanoTime() - start) / 1000000);
					gearCenterline = gp.centerline;
					gearArea = gp.area;
					if(gearCenterline == 0){
						gearCenterline = -1;
					}
				}
			  }
			  oldMode = mode;
		  }
	  }
	  

	private static UsbCamera setUsbCamera2(int cameraId, MjpegServer server) {
	  UsbCamera camera = new UsbCamera("GearCamera", cameraId);
	  server.setSource(camera);
	  return camera;
	}
}

