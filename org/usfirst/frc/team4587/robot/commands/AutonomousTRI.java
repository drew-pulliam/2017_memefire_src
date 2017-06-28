package org.usfirst.frc.team4587.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import utility.Gyro;

/**
 *
 */
public class AutonomousTRI extends CommandGroup {

    public AutonomousTRI() {
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
    	//addSequential(new RaiseGearIntake());
    	addSequential(new LowerBallIntake());
    	addSequential(new HopperOut());
    	addSequential(new FollowChezyPath("TRIPath", true/*backwards*/, false,-1,0.0));
    	addSequential(new Delay(10));
    	addSequential(new EjectGear());
    	/*addSequential(new Delay(25));
    	addSequential(new ToggleGearIntakeMotors());
    	addSequential(new FollowChezyPath(1));*/
    }
}
