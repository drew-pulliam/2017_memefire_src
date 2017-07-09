package utility;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
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
	  double height;
	  double centerline;
	  double time;
	  public void run()
	  {
		  PrintStream w=null;
		  try{
		  w = new PrintStream("/home/lvuser/cameraThreadLog.csv");
		  }catch(Exception e){}
		  w.println("run");
		  SmartDashboard.putNumber("visionHeight", -50);
		  SmartDashboard.putNumber("visionCenterline", -50);
		  SmartDashboard.putNumber("visionTime", -50);
		  SmartDashboard.putBoolean("ConnectedToPi", false);
		  ServerSocket serverSocket = null;
		  Socket clientSocket = null;
	      PrintWriter clientSocketWriter = null ;
		  try{
			  serverSocket = new ServerSocket(1189);
			  w.println("about to accept");
			  while(on){
				  clientSocket = serverSocket.accept();
				  clientSocketWriter = new PrintWriter ( clientSocket.getOutputStream(), /*autoFlush=*/true );
				  SmartDashboard.putBoolean("ConnectedToPi", true);
				    BufferedReader in = new BufferedReader(
				            new InputStreamReader(clientSocket.getInputStream()));
				  String line;
				  w.println("before while");
				  while((line = in.readLine())!=null){
					  long sysTime = System.nanoTime()/1000000;
					  w.println("after while");
					  if(on == false)
						  break;
					  String[] tokens = line.split(",");
					  if(tokens.length != 3){
						  w.println("BAD Stuff");
						  continue;
					  }
					  height = Double.parseDouble(tokens[0]);
					  centerline = Double.parseDouble(tokens[1]);
					  time = (sysTime-Double.parseDouble(tokens[2]))*1000000;
					  if(time<0){
						  time=0;
					  }
					  SmartDashboard.putNumber("visionHeight", height);
					  SmartDashboard.putNumber("visionCenterline", centerline);
					  SmartDashboard.putNumber("visionTime", time);
					  clientSocketWriter.println("alive");
				  }
				  try{in.close();}catch(Exception e){}
				  try{clientSocketWriter.close();}catch(Exception e){}
				  try{clientSocket.close();}catch(Exception e){}
			  }
		  }catch(Exception e){
			  w.println(e);
		  }
		  finally
		  {
			  w.println("finally");
			  try{clientSocket.close();}catch(Exception e){}
			  try{serverSocket.close();}catch(Exception e){}
			  w.close();
		  }
	  }
	  public double[] getValues(){
		  double[] values = new double[3];
		  values[0]=time;
		  values[1]=centerline;
		  values[2]=height;
		  return values;
	  }
	  public synchronized void turnOff()
	  {
		  on = false;
		  System.err.println("turnOff");
	  }
	  public synchronized void turnOn()
	  {
		  on = true;
		  System.err.println("turnOn");
	  }
}

