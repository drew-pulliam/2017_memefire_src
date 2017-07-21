package org.usfirst.frc.team4587.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import utility.Gyro;

/**
 *
 */
public class AutoGearSide1 extends CommandGroup {

    public AutoGearSide1(String side) {
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
    	//addSequential(new FollowChezyPath("RightGearPath0", false, false,1,Gyro.getYaw()));
    	if(left){
        	addSequential(new FollowChezyPath("sideGearPath", true, true,1,0));//true,-1 turns left, right side longer distance
        	addSequential(new Delay(10));
        	addSequential(new EjectGear());
        	addSequential(new FollowChezyPath("sideGearDownfieldPath0", true, true,1,0));
        	addSequential(new AutonomousTurnSimple(-45));
        	addSequential(new FollowChezyPath("sideGearDownfieldPath1", true, true,1,0));
        	//addSequential(new FollowChezyPath("sideGearPathweird", true, true,1,Gyro.getYaw()));//true,-1 turns left, right side longer distance
    	}else{
        	addSequential(new FollowChezyPath("sideGearPath", true, false,-1,0));
        	addSequential(new Delay(10));
        	addSequential(new EjectGear());
        	addSequential(new FollowChezyPath("sideGearDownfieldPath0", true, false,-1,0));
        	addSequential(new AutonomousTurnSimple(45));
        	addSequential(new FollowChezyPath("sideGearDownfieldPath1", true, false,-1,0));
        	//addSequential(new FollowChezyPath("sideGearPathweird", true, false,-1,Gyro.getYaw()));//true,-1 turns left, right side longer distance
    	}
    	//addSequential(new AutonomousTurnToAngleSimple(angle));
    	//addSequential(new Delay(10));
    	//addSequential(new FollowChezyPath("RightGearPath1",false,false,1,Gyro.getYaw()));

    	//addSequential(new Delay(20));
    	//addSequential(new EjectGear());
    	/*addSequential(new Delay(25));
    	addSequential(new ToggleGearIntakeMotors());
    	addSequential(new FollowChezyPath(1));*/
    }
}
