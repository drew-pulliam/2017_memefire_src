package org.usfirst.frc.team4587.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import utility.Gyro;

/**
 *
 */
public class AutoHopper extends CommandGroup {

    public AutoHopper(String side) {
    	boolean blue;
    	if(side.equals("blue")){
    		blue = true;
    	}else{
    		blue = false;
    	}
    	if(blue){
        	addSequential(new BallIntakeDown());
        	addSequential(new FollowChezyPath("hopperPath",false,true,-1,0));
        	addSequential(new BallIntakeUp());
        	//addSequential(new DriveForwardSlowHopper());
        	addSequential(new Delay(70));
        	//addSequential(new ToggleFlywheelRunning(true, 3020));
        	addSequential(new FollowChezyPath("hopperPath2",false,true,-1,Gyro.getYaw()));
        	addSequential(new AutonomousTurnSimple(-60));
        	addSequential(new AimAndShoot());
        	//addSequential(new ShootBalls(true));
        	//addSequential(new Vision());
    	}else{
        	addSequential(new BallIntakeDown());
        	addSequential(new FollowChezyPath("hopperPath",false,false,1,0));
        	addSequential(new BallIntakeUp());
        	//addSequential(new DriveForwardSlowHopper());
        	addSequential(new Delay(70));
        	//addSequential(new ToggleFlywheelRunning(true, 3020));
        	addSequential(new FollowChezyPath("hopperPath2",false,false,1,Gyro.getYaw()));
        	addSequential(new AutonomousTurnSimple(60));
        	addSequential(new AimAndShoot());
        	//addSequential(new ShootBalls(true));
        	//addSequential(new Vision());
    	}
    	//addSequential(new Shoot());
    	/*addSequential(new Delay(25));
    	addSequential(new ToggleGearIntakeMotors());
    	addSequential(new FollowChezyPath(1));*/
    }
}
