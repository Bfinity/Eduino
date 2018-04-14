package org.usfirst.frc4680.Eduino.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveABC extends DriveDeltaXY {

    final double switchDistance = 100 - 28;
    final double fallBackDistance = 170 - 28;
    final double scaleDistance = 260 - 28;
    char mySide;
    
    public DriveABC(char side) {
    	mySide = side;
    }
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	String gameData;
    	boolean ourSwitch = false;
    	boolean ourScale = false;
    	
    	
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println("got game data: " + gameData);
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
		  
		  if(ourScale == true ){
			  deltaX = scaleDistance;
			  if(mySide == 'R') {
				  deltaY = 20;
			  } else {
				  deltaY = -20;
			  }
		  } else if( ourSwitch == true ){
			  deltaX = switchDistance;
			  deltaY = 0;
		  } else {
			  deltaX = fallBackDistance;
			  deltaY = 0;
		  }
		  
          super.initialize();
    }
}
