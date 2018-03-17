package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Score extends Command {
	char mySide;
	
    public Score(char side) {
    	mySide = side;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	String gameData;
    	boolean ourSwitch = false;
    	boolean ourScale = false;
    	
    	
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		//System.out.println("got game data: " + gameData);
        if(gameData.length() > 0)
        {
		  if(gameData.charAt(0) == mySide) {
			  ourSwitch = true;
		  } else {
			  ourSwitch = false;
		  }
        
		  if(gameData.charAt(1) == mySide){
			  ourScale = true;
		  }else {
			  ourScale = false;
		  }
        }
		  
		  if(ourSwitch == true || ourScale == true ) {
			  Robot.grabber.open();
		  }
		  
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
