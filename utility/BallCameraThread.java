package utility;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoProperty;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallCameraThread extends Thread{
	  String oldMode = "startUp";
	  String mode = "ComputerVision";
	  MjpegServer cameraStream;
	  UsbCamera camera2;
	  int streamCameraPort = 5805;
	  CvSink imageSink;
	  MjpegServer cvStream;
	  GripPipeline gp;
	  Mat inputImage;
	  Mat hsv;
	  CvSource imageSource;
	  
	  private double ballCenterline=-2;
	  private double ballArea;
	  private double ballHeight;
	  private double ballTime;
	  
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
					  VideoProperty wbm = camera2.getProperty("white_balance_temperature");
					  camera2.setWhiteBalanceManual(wbm.getMin());
					  camera2.setExposureManual(0);
			  }
			  else
			  {
				  if(oldMode.equals("ComputerVision") == false)
				  {
					  VideoProperty wbm = camera2.getProperty("white_balance_temperature");
					  camera2.setWhiteBalanceManual(wbm.getMin());
					  camera2.setExposureManual(0);
				  }
					//ballCenterline= -1;
				long start = System.nanoTime();
				long frameTime = imageSink.grabFrame(inputImage);
				if (frameTime != 0) 
				{
					//Imgproc.cvtColor(inputImage, hsv, Imgproc.COLOR_BGR2HSV);
					//Core.inRange(hsv, new Scalar(70.0, 200.0, 70),
					//	new Scalar(100.0, 255.0, 120), hsv);
					gp.process(inputImage);
					//imageSource.putFrame(gp.hsvThresholdOutput());
					imageSource.putFrame(inputImage);
					ballCenterline = gp.centerline;
					System.out.println("ballCenterline: "+ballCenterline);
					SmartDashboard.putNumber("rio.centerline", ballCenterline);
					SmartDashboard.putNumber("rio.height", gp.height);
					SmartDashboard.putNumber("rio.area", gp.area);
					SmartDashboard.putNumber("rio.time", start);//(System.nanoTime() - start) / 1000000);
					
					ballArea = gp.area;
					ballHeight = gp.height;
					ballTime = start;
					if(ballCenterline == 0){
						ballCenterline = -1;
					}
				}
			  }
			  oldMode = mode;
		  }
	  }

	  public double getBallCenterline()
	  {
		  return ballCenterline;
	  }
	  public double getBallArea()
	  {
		  return ballArea;
	  }
	  public double getBallHeight(){
		  return ballHeight;
	  }
	  public double getBallTime(){
		  return ballTime;
	  }

	private static UsbCamera setUsbCamera2(int cameraId, MjpegServer server) {
	  UsbCamera camera = new UsbCamera("GearCamera", cameraId);
	  server.setSource(camera);
	  return camera;
	}

}

