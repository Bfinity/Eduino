package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;
import org.usfirst.frc4680.Eduino.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDeltaXY extends Command {
	
	public static final double maxSpeed   = 1.0;
	public static final double minSpeed   = 0.2;
	public static final double rampUpTime = 1.0;	
	public static final double rampDownDistance = 36.0;
	public static final double closeEnough = 1.0;

	double finalX;
	double finalY;
	double deltaX;
	double deltaY;
	double heading;
	long startTime;
	
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
		heading = Robot.driveTrain.getHeading();
		startTime = RobotController.getFPGATime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		double remainingX = finalX - Robot.driveTrain.getFwdBwdDistance();
    		double remainingY = finalY - Robot.driveTrain.getLeftRightDistance();
    		
    		double speedX = limitSpeed(remainingX);
    		double speedY = limitSpeed(remainingY);
    		
    		Robot.driveTrain.keepHeadingDrive(speedX, speedY, heading);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		double remainingX = finalX - Robot.driveTrain.getFwdBwdDistance();
		double remainingY = finalY - Robot.driveTrain.getLeftRightDistance();
		if(Math.abs(remainingY) > closeEnough || Math.abs(remainingX) > closeEnough) {
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
    
    public double limitSpeed(double distance) {
		double speed = maxSpeed;
		double dist = Math.abs(distance);

		if(getElapsedTime()<rampUpTime){
			speed = getElapsedTime()/rampUpTime;
		} else {
			speed = maxSpeed;
		}
		
		if(dist > rampDownDistance) {
			// no change
		} else if(dist > closeEnough) {
			speed = Math.min(speed, distance/rampDownDistance);
			speed = Math.max(speed, minSpeed);
		} else {
			speed = 0.0;
		}
			
		return Math.signum(distance) * speed;
	}

    
    double getElapsedTime() {
    		return (RobotController.getFPGATime() - startTime) / 1.0e6;
    }
}
