package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ToggleFlywheelRunning extends Command {

	double m_rpms;
	boolean run;
    public ToggleFlywheelRunning(boolean runx,double rpms) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	System.out.println("ctor-ToggleFlywheelRunning("+(runx?"true":"false")+","+rpms+")");
    	requires(Robot.getFlywheel());
    	run = runx;
    	m_rpms = rpms;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.getFlywheel().setRunning(!Robot.getFlywheel().running());
    	//Robot.getElevator().setRunning(!Robot.getElevator().running());
    	if (run == false)
    	{
    		Robot.getFlywheel().setRunning(false);
    		//Robot.getIndexer().setRunning(false);
    		Robot.getFlywheel().setSetpoint(0.0);
    		Robot.getFlywheel().disable();
    		//Robot.getIndexer().disable();
    		System.out.println("not running");
    	}
    	else
    	{
    		System.out.println("running");
    		Robot.getFlywheel().initialize();
    		Robot.getFlywheel().enable();
    		//Robot.getIndexer().enable();
    		Robot.getFlywheel().setRunning(true);
    		//Robot.getIndexer().setRunning(true);
    		double m_motorLevel;
    		//m_rpms = SmartDashboard.getNumber("FlywheelVelocity", 1850);
        	//m_motorLevel = m_rpms / 6750 * 1.35;//6750 = max rpms
        	m_motorLevel =( m_rpms + 1000) / 6750 *1.1;//6750 = max rpms
        	Robot.getFlywheel().setSetpoint(m_rpms);
        	Robot.getFlywheel().setExpectedMotorLevel(m_motorLevel);
        	//SmartDashboard.putNumber("Flywheel RPM's set to: ", m_rpms);
        	SmartDashboard.putNumber("FlywheelVelocity", m_rpms);
        	//Robot.getIndexer().setSetpoint(m_rpms);
        	//Robot.getIndexer().setExpectedMotorLevel(m_motorLevel);
        	System.out.println(m_rpms + " " + m_motorLevel + " setpoint: " + Robot.getFlywheel().getSetpoint());
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    }
}
