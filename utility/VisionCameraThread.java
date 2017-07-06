package utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoProperty;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCameraThread extends Thread{
	
	 boolean on = true;
	  
	  public void run()
	  {
		  SmartDashboard.putNumber("visionHeight", -50);
		  SmartDashboard.putNumber("visionCenterline", -50);
		  SmartDashboard.putNumber("visionTime", -50);
		  SmartDashboard.putBoolean("ConnectedToPi", false);
		  ServerSocket serverSocket = null;
		  Socket clientSocket = null;
		  try{
			  serverSocket = new ServerSocket(1200);
			  clientSocket = serverSocket.accept();
			  SmartDashboard.putBoolean("ConnectedToPi", true);
			    BufferedReader in = new BufferedReader(
			            new InputStreamReader(clientSocket.getInputStream()));
			  char[] inBuffer = new char[256];
			  int inRead;
			  String line;
			  while((inRead = in.read(inBuffer))>=0){
				  if(on == false)
					  break;
				  line = new String(inBuffer);
				  String[] tokens = line.split(",");
				  if(tokens.length != 3){
					  System.out.println("BAD Stuff");
					  continue;
				  }
				  double height = Double.parseDouble(tokens[0]);
				  double centerline = Double.parseDouble(tokens[1]);
				  double time = Double.parseDouble(tokens[2]);
				  SmartDashboard.putNumber("visionHeight", height);
				  SmartDashboard.putNumber("visionCenterline", centerline);
				  SmartDashboard.putNumber("visionTime", time);
			  }
		  }catch(Exception e){
			  
		  }
		  finally
		  {
			  try{clientSocket.close();}catch(Exception e){}
			  try{serverSocket.close();}catch(Exception e){}
		  }
	  }
	  
	  public synchronized void turnOff()
	  {
		  on = false;
	  }
}

