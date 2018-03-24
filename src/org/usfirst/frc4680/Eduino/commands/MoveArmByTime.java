package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveArmByTime extends Command {
	
	double commandSpeed;
	
    public MoveArmByTime(double speed, double time) {
    		commandSpeed = speed;
    		setTimeout(time);
        requires(Robot.longArm);
    }

    protected void execute() {
    		Robot.longArm.move(commandSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    		Robot.longArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		Robot.longArm.stop();
    }
}
