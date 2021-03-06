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
import com.ctre.phoenix.motorcontrol.NeutralMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
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

    private boolean brakeState = false;
    
    public int countL;
    public int countR;

    
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
	        countR = cANTalonBackRight.getSensorCollection().getQuadraturePosition();
	    	countL = -cANTalonBackLeft.getSensorCollection().getQuadraturePosition();
	    	
	    	double dist = (countL + countR)/2 * inchesPerEncCountFB;
	    	return dist;
    }
    
    public double getRightwardDistance(){
	    	int countR = cANTalonBackRight.getSensorCollection().getQuadraturePosition();
	    	int countL = cANTalonBackLeft.getSensorCollection().getQuadraturePosition();

	    	double dist = (countL + countR)/2 * inchesPerEncCountLR;
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
		SmartDashboard.putNumber("heading", getHeading());
		SmartDashboard.putNumber("forwardDistance", Robot.driveTrain.getFwdBwdDistance());
		SmartDashboard.putNumber("rightwardDistance", Robot.driveTrain.getRightwardDistance());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void teleopDrive(double fwdBackCommand, double leftRightCommand, double yawCommand)
    {
    		mecanumDrive1.driveCartesian(leftRightCommand, fwdBackCommand, yawCommand);
    }

    public void keepHeadingDrive(double forwardCommand, double rightwardCommand, double heading)
    {
		double angle = Robot.driveTrain.getHeading();
		double currentAngularRate = Robot.driveTrain.getAngularRate();
		double angle_error = DriveTrain.angleDelta(heading, angle);
		double yawCommand = - angle_error * kPgain - (currentAngularRate) * kDgain;
		
		SmartDashboard.putNumber("angle_error", angle_error);
		SmartDashboard.putNumber("commanded heading", heading);
		//System.out.println("keepHeadingDrive: " + forwardCommand +","+ rightwardCommand +","+ heading  +","+ angle_error); 

        mecanumDrive1.driveCartesian(rightwardCommand, forwardCommand, yawCommand);
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

	public void resetPosition() {
		// TODO Auto-generated method stub
		gyro.reset();
		cANTalonBackRight.getSensorCollection().setQuadraturePosition(0, 0);
		cANTalonBackLeft.getSensorCollection().setQuadraturePosition(0, 0);
		
	}

	public void brake(boolean brakeOn) {
	    if(brakeOn != brakeState){
            if(brakeOn) {
                	cANTalonBackRight.setNeutralMode(NeutralMode.Brake);
                	cANTalonFrontRight.setNeutralMode(NeutralMode.Brake);
                	cANTalonBackLeft.setNeutralMode(NeutralMode.Brake);
                	cANTalonFrontLeft.setNeutralMode(NeutralMode.Brake);	
                	mecanumDrive1.stopMotor();
            } else {
                	cANTalonBackRight.setNeutralMode(NeutralMode.Coast);
                	cANTalonFrontRight.setNeutralMode(NeutralMode.Coast);
                	cANTalonBackLeft.setNeutralMode(NeutralMode.Coast);
                	cANTalonFrontLeft.setNeutralMode(NeutralMode.Coast);	
            }
	    }
	    brakeState = brakeOn;
	}
}

