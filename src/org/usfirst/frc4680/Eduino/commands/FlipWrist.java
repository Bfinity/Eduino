package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlipWrist extends Command {

	double startPosition;
	double finishPosition;
	int step = 0;
	
    public FlipWrist() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startPosition = Robot.driveTrain.getFwdBwdDistance();
    	finishPosition = startPosition + 14;
    	step = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(step == 0) {
    		Robot.driveTrain.keepHeadingDrive(1.0, 0.0, 0);
    		if(Robot.driveTrain.getFwdBwdDistance() > finishPosition) {
    			step = 1;
    		}
    	} else if(step == 1) {
    		Robot.driveTrain.keepHeadingDrive(-.3, 0, 0);
	    	if(Robot.driveTrain.getFwdBwdDistance()< 1)	{
	    		Robot.driveTrain.stop();
	    		step = 2;
	    	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(step > 1) {
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
    }
}
