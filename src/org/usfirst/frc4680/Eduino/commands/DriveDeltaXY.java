package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDeltaXY extends Command {
	
	public static final double maxSpeed   = 1.0;
	public static final double minSpeedFB   = 0.18;
	public static final double minSpeedLR   = 0.3;
	public static final double rampUpTimeFB = 1.0;	
	public static final double rampUpTimeLR = 1.1;	
	public static final double rampDownDistanceFB = 36.0;
	public static final double rampDownDistanceLR = 24.0;
	public static final double closeEnough = 2.0;

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
    		System.out.println("DriveDeltaXY fwd: " + deltaX + "right: " + deltaY);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
		finalX = Robot.driveTrain.getFwdBwdDistance() + deltaX;
		finalY = Robot.driveTrain.getRightwardDistance() + deltaY;
		heading = Robot.driveTrain.getHeading();
		startTime = RobotController.getFPGATime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		double remainingX = finalX - Robot.driveTrain.getFwdBwdDistance();
    		double remainingY = finalY - Robot.driveTrain.getRightwardDistance();
    		
    		double speedX = limitSpeedFB(remainingX);
    		double speedY = limitSpeedLR(remainingY);
    		
    		Robot.driveTrain.keepHeadingDrive(speedX, speedY, heading);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		double remainingX = finalX - Robot.driveTrain.getFwdBwdDistance();
		double remainingY = finalY - Robot.driveTrain.getRightwardDistance();
		if(Math.abs(remainingY) > closeEnough || Math.abs(remainingX) > closeEnough) {
			return false;
		} else if(getElapsedTime() > 5.0){
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
    	Robot.driveTrain.stop();
    }
    
    public double limitSpeedLR(double distance) {
		double speed = maxSpeed;
		double dist = Math.abs(distance);

		if(getElapsedTime()<rampUpTimeLR){
			speed = getElapsedTime()/rampUpTimeLR;
		} else {
			speed = maxSpeed;
		}
		
		if(dist > rampDownDistanceLR) {
			// no change
		} else if(dist > closeEnough) {
			speed = Math.min(speed, distance/rampDownDistanceLR);
			speed = Math.max(speed, minSpeedLR);
		} else {
			speed = 0.0;
		}
			
		return Math.signum(distance) * speed;
	}

    
    public double limitSpeedFB(double distance) {
		double speed = maxSpeed;
		double dist = Math.abs(distance);

		if(getElapsedTime()<rampUpTimeFB){
			speed = getElapsedTime()/rampUpTimeFB;
		} else {
			speed = maxSpeed;
		}
		
		if(dist > rampDownDistanceFB) {
			// no change
		} else if(dist > closeEnough) {
			speed = Math.min(speed, distance/rampDownDistanceFB);
			speed = Math.max(speed, minSpeedFB);
		} else {
			speed = 0.0;
		}
			
		return Math.signum(distance) * speed;
	}

    
    double getElapsedTime() {
    		return (RobotController.getFPGATime() - startTime) / 1.0e6;
    }
}
