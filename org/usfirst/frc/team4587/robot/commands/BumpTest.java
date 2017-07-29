package org.usfirst.frc.team4587.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import utility.Gyro;

/**
 *
 */
public class BumpTest extends CommandGroup {

    public BumpTest(String side) {
    	boolean blue;
    	if(side.equals("blue")){
    		blue = true;
    	}else{
    		blue = false;
    	}
    	
    	if(blue){
        	addSequential(new FollowChezyPath("bumpHopperPath",true,false,-1,0));
        	addSequential(new HopperOut());
        	addSequential(new Delay(10));
        	addSequential(new FollowChezyPath("aimHopperPath",true,true,1,0));
    	}else{
        	addSequential(new FollowChezyPath("bumpHopperPath",true,true,1,0));
        	addSequential(new HopperOut());
        	addSequential(new Delay(10));
        	addSequential(new FollowChezyPath("aimHopperPath",true,false,-1,0));
    	}
    }
}
