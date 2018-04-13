package org.usfirst.frc4680.Eduino.subsystems;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 *	Assumptions:
 *	Y-axis is Forward (North)
 *	X-axis is to the Right (East)
 *	Theta is the heading angle in radians,
 *      increasing counter-clockwise. 
 */
public class InertialNavigation extends Subsystem {

	public enum UnitType { Meters, Inches }
	
	Accelerometer accelerometer;
	double velX, velY, posX, posY;
	double kUnit, rioOffsetAngle;
	
    public InertialNavigation(double angle) {
    		accelerometer = new BuiltInAccelerometer();
    		velX = 0;
    		velY = 0;
    		posX = 0;
    		posY = 0;
    		rioOffsetAngle = Math.toRadians(angle);
    		
    		setUnits(UnitType.Meters);
    }
	

	@Override
	public void periodic() {
		
		double accelXrio = 9.8 * accelerometer.getX();
		double accelYrio = 9.8 * accelerometer.getY();
		
		double deltaVelXrio = accelXrio * Robot.DEFAULT_PERIOD;
		double deltaVelYrio = accelYrio * Robot.DEFAULT_PERIOD;
		
		double theta = rioOffsetAngle + getTheta();
		
		double deltaVelX = deltaVelXrio * Math.cos(theta) +
				           deltaVelYrio * Math.sin(theta);
		double deltaVelY = deltaVelXrio * Math.sin(theta) +
						   deltaVelYrio * Math.cos(theta);
		
		velX += deltaVelX;
		velY += deltaVelY;
		
		posX += velX * Robot.DEFAULT_PERIOD;
		posY += velY * Robot.DEFAULT_PERIOD; 
	}
	
	public void setUnits(UnitType u) {
		switch(u) {
		case Inches:
			kUnit = 39.37;
			break;
		case Meters: 
		default:
			kUnit = 1.0;
			break;
		}
	}
	
	public double getX() { return kUnit * posX; }
	public double getY() { return kUnit * posY; }
	
	public double getTheta() {
		return Math.toRadians(-Robot.driveTrain.getHeading());
	}


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void reset() {
		posX = 0;
		posY = 0;
		velX = 0;
		velY = 0;
	}
	
	public void stop() {
		velX = 0;
		velY = 0;
	}
}
