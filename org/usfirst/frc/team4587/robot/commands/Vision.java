package org.usfirst.frc.team4587.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import utility.Gyro;

/**
 *
 */
public class Vision extends CommandGroup {

    public Vision() {

    	addSequential(new Aim());
    	addSequential(new AimDist());
    }
}
