// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4680.Eduino.subsystems;

import org.usfirst.frc4680.Eduino.Robot;
import org.usfirst.frc4680.Eduino.RobotMap;
import org.usfirst.frc4680.Eduino.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	public static final double fastSpeed   = 0.5;
	public static final double mediumSpeed = 0.4;
	public static final double slowSpeed   = 0.3;
	
	public static final double farDistance   = 48.0;
	public static final double closeDistance = 24.0;
	public static final double closeEnough   =  1.0;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX cANTalonFrontRight = RobotMap.driveTrainCANTalonFrontRight;
    private final WPI_TalonSRX cANTalonFrontLeft = RobotMap.driveTrainCANTalonFrontLeft;
    private final WPI_TalonSRX cANTalonBackRight = RobotMap.driveTrainCANTalonBackRight;
    private final WPI_TalonSRX cANTalonBackLeft = RobotMap.driveTrainCANTalonBackLeft;
    private final MecanumDrive mecanumDrive1 = RobotMap.driveTrainMecanumDrive1;
    private final ADXRS450_Gyro gyro = RobotMap.driveTrainGyro;
    private final DigitalInput frontBumperContactSwitch = RobotMap.driveTrainFrontBumperContactSwitch;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    //TODO calibrate this constant
    static final double inchesPerEncCountFB = (17.78 / 4096);
    static final double inchesPerEncCountLR = (20.34 / 4096);
    static final double kPgain =  0.040; /* percent throttle per degree of error */
    static final double kDgain =  0.005; /* percent throttle per angular velocity dps */


    
    public DriveTrain(){
    	cANTalonFrontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    	cANTalonFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    	cANTalonBackRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    	cANTalonBackLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    	
    	cANTalonFrontRight.getSensorCollection().setQuadraturePosition(0, 10);
    	cANTalonFrontLeft.getSensorCollection().setQuadraturePosition(0, 10);
    	cANTalonBackRight.getSensorCollection().setQuadraturePosition(0, 10);
    	cANTalonBackLeft.getSensorCollection().setQuadraturePosition(0, 10);
    }
    
    public double getFwdBwdDistance(){
	    	int countR = cANTalonFrontRight.getSensorCollection().getQuadraturePosition();
	    	int countL = -cANTalonFrontLeft.getSensorCollection().getQuadraturePosition();
	    	
	    	double dist = (countL + countR)/2 * inchesPerEncCountFB;
	    	return dist;
    }
    
    public double getLeftRightDistance(){
	    	int countR = cANTalonFrontRight.getSensorCollection().getQuadraturePosition();
	    	int countL = cANTalonFrontLeft.getSensorCollection().getQuadraturePosition();

	    	double dist = - (countL + countR)/2 * inchesPerEncCountLR;
	    	return dist;
    }
   
    public double getHeading() {
    		return gyro.getAngle();
    }
    
    public double getAngularRate() {
    		return gyro.getRate();
    }
    
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new TeleOpDrive());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void teleopDrive(double fwdBackCommand, double leftRightCommand, double yawCommand)
    {
    		mecanumDrive1.driveCartesian(leftRightCommand, fwdBackCommand, yawCommand);
    }

    public void keepHeadingDrive(double fwdBackCommand, double leftRightCommand, double heading)
    {
		double angle = Robot.driveTrain.getHeading();
		double currentAngularRate = Robot.driveTrain.getAngularRate();
		double angle_error = DriveTrain.angleDelta(heading, angle);
		double yawCommand = - angle_error * kPgain - (currentAngularRate) * kDgain;
		
		SmartDashboard.putNumber("angle_error", angle_error);
		SmartDashboard.putNumber("commanded heading", heading);

    		mecanumDrive1.driveCartesian(leftRightCommand, fwdBackCommand, yawCommand);
    }

	public void stop() {
		mecanumDrive1.stopMotor();	
	}

    static public double angleDelta(double src, double dest) {
		double delta = (dest - src) % 360.0;
		if(Math.abs(delta) > 180) {
			delta = delta - (Math.signum(delta) * 360);
		}
		return delta;
    }

    public static double chooseSpeed(double distance) {
		double speed;
		double dist = Math.abs(distance);
		if(dist > farDistance) {
			speed = fastSpeed;
		} else if(dist > closeDistance) {
			speed = mediumSpeed;
		} else if(dist > closeEnough) {
			speed = slowSpeed;
		} else {
			speed = 0.0;
		}
			
			return Math.signum(distance) * speed;
	}


}

