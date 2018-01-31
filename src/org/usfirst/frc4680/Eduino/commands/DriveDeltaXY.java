package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;
import org.usfirst.frc4680.Eduino.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDeltaXY extends Command {
	
	double finalX;
	double finalY;
	double deltaX;
	double deltaY;
	
    public DriveDeltaXY() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        
    }

    public DriveDeltaXY(double forwardDist, double rightwardDist) {
    		deltaX = forwardDist;
    		deltaY = rightwardDist;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
		finalX = Robot.driveTrain.getFwdBwdDistance() + deltaX;
		finalY = Robot.driveTrain.getLeftRightDistance() + deltaY;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		double remainingX = finalX - Robot.driveTrain.getFwdBwdDistance();
    		double remainingY = finalY - Robot.driveTrain.getLeftRightDistance();
    		
    		double speedX = DriveTrain.chooseSpeed(remainingX);
    		double speedY = DriveTrain.chooseSpeed(remainingY);
    		
    		Robot.driveTrain.teleopDrive(speedX, speedY, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		double remainingX = finalX - Robot.driveTrain.getFwdBwdDistance();
		double remainingY = finalY - Robot.driveTrain.getLeftRightDistance();
		if(Math.abs(remainingY) > DriveTrain.closeEnough || Math.abs(remainingX) > DriveTrain.closeEnough) {
			return false;
		} else {
			return true;
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
