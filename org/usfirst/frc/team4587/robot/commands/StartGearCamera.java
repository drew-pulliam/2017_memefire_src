package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartGearCamera extends Command {

	boolean cameraOn;

    public StartGearCamera() {
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {/*
    	if(cameraOn == false){
    		System.out.println("start plz");
    		try{
    			//CameraServer.getInstance().startAutomaticCapture().setResolution(64, 48);
    			System.out.println("Starting the camera!"); //I only see this printed once in the output, ever.
    			UsbCamera camObject = CameraServer.getInstance().startAutomaticCapture();
    			CvSink cvSink = CameraServer.getInstance().getVideo();
    			CameraServer.getInstance().putVideo(cvSink, 64, 48)

    			//the following code does exactly what CameraServer.putVideo() does; I had putVideo before but the same problem existed.

    			CvSource cvSource = new CvSource("ContourVideo", VideoMode.PixelFormat.kMJPEG, 320, 240, 30);
    			CameraServer.getInstance().addCamera(cvSource);
    			VideoSink server = CameraServer.getInstance().addServer("serve_" + cvSource.getName());
    			server.setSource(cvSource);
    			cameraOn = true;
    		}catch(Exception e){
    			System.out.println(e);
    		}
    	}else{
    		System.out.println("don't start plz");
    	}*/
    	CameraServer.getInstance().startAutomaticCapture(0).setResolution(160, 120);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
