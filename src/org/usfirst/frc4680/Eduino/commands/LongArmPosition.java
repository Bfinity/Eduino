package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class LongArmPosition extends PIDCommand {
	
    public static final int FLOOR_HEIGHT = 0;
    public static final int SWITCH_HEIGHT = 86;
    public static final int SCALE_HEIGHT = 137;
    int target;

	public LongArmPosition(int tgt) {
    	super("PIDCommand1", 0.5, 0.0, 0.0, 0.02);
		target = tgt;
        getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(5.0);
        getPIDController().setOutputRange(-0.4, 0.9);
        getPIDController().setSetpoint((double) target);
        
        requires(Robot.longArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        getPIDController().setSetpoint((double) target);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println("LongArmPosition: " + target + " " + Robot.longArm.getPosition());
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return getPIDController().onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    		getPIDController().disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	getPIDController().disable();
    }

	@Override
	protected double returnPIDInput() {
		return (double) Robot.longArm.getPosition();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.longArm.move(output);
	}
}
