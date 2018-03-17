package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnTo extends Command {

	double targetDirection;
	
    public TurnTo(double tgt) {
        targetDirection = tgt;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.keepHeadingDrive(0, 0, targetDirection);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Math.abs(Robot.driveTrain.getHeading() - targetDirection) < 2) {
    		return true;
    	} else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
}
