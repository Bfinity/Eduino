package org.usfirst.frc4680.Eduino.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetHeight extends LongArmPosition {

	char mySide;
	
    public SetHeight(char side) {
		super(SWITCH_HEIGHT);
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
		  
		  if(ourSwitch == true && ourScale == false ) {
			  target = SWITCH_HEIGHT;
		  } else if(ourSwitch == false && ourScale == true  ){
			  target = SCALE_HEIGHT;
		  } else if( ourSwitch == true && ourScale == true){
			  target = SWITCH_HEIGHT;
		  } else if(ourSwitch == false && ourScale == false){
			  target = SWITCH_HEIGHT;
		  }
		  
		  System.out.println("SetHeight: " + target + " " + ourSwitch + " " + ourScale);
		  
          super.initialize();
       
    }

}
