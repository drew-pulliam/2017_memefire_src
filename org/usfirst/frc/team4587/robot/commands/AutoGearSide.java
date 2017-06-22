package org.usfirst.frc.team4587.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import utility.Gyro;

/**
 *
 */
public class AutoGearSide extends CommandGroup {

    public AutoGearSide(String side) {
    	boolean left;
    	if(side.equals("left")){
    		left = true;
    	}
    	else
    	{
    		left = false;
    	}
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	//addSequential(new AutonomousDriveStraightDistance(100, 0.55));
    	addSequential(new RaiseGearIntake());
    	if(left){
        	addSequential(new FollowChezyPath("sideGearPathWorlds", false, false,1,Gyro.getYaw()));
        	addSequential(new Delay(10));
        	//addSequential(new Delay(10));
        	addSequential(new FollowChezyPath("SideGearPathWorlds2",false,false,1,60));
    	}else{
        	addSequential(new FollowChezyPath("sideGearPathWorlds", false, true,-1,Gyro.getYaw()));
        	addSequential(new Delay(10));
        	//addSequential(new Delay(10));
        	addSequential(new FollowChezyPath("SideGearPathWorlds2",false,false,1,-60));
    	}

    	addSequential(new Delay(20));
    	addSequential(new EjectGear());
    	/*addSequential(new Delay(25));
    	addSequential(new ToggleGearIntakeMotors());
    	addSequential(new FollowChezyPath(1));*/
    }
}
