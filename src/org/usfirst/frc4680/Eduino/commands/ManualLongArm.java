package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualLongArm extends Command {

    public ManualLongArm() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.longArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		double longArmInput = Robot.oi.manipulatorStick.getRawAxis(1);
		double scaledLongArmInput = longArmInput * Math.abs(longArmInput);
		Robot.longArm.move(scaledLongArmInput);
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
