package org.usfirst.frc.team4587.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import utility.Gyro;

/**
 *
 */
public class HopperAutoSimple extends CommandGroup {

    public HopperAutoSimple(String side) {
    	double angle;
    	if (side.equals("blue")){
    		angle = -90;
    	}else{
    		angle = 90;
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
    	addSequential(new ToggleFlywheelRunning(true,3070));
    	addSequential(new RaiseGearIntake());
    	addSequential(new HopperOut());
    	addSequential(new FollowChezyPath("HopperPath0", false, false,1,Gyro.getYaw()));
    	addSequential(new Delay(5));
    	addSequential(new AutonomousTurnToAngleSimple(angle));
    	addSequential(new Delay(15));
    	//addSequential(new SetScytheAndShintake(0.0,1.0,0));
    	addSequential(new FollowChezyPath("HopperPath1", false, false,1,angle));
    	addSequential(new HopperOn());
    	//addSequential(new SetScytheAndShintake(0.6,1.0,0));
    	/*addSequential(new Delay(25));
    	addSequential(new ToggleGearIntakeMotors());
    	addSequential(new FollowChezyPath(1));*/
    }
}
