package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualWrist extends Command {

    public ManualWrist() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		double wristInput = Robot.oi.manipulatorStick.getRawAxis(5);
		double scaledWristInput = wristInput * Math.abs(wristInput);
		Robot.wrist.move(scaledWristInput);    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}