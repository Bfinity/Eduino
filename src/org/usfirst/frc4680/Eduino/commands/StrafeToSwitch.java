package org.usfirst.frc4680.Eduino.commands;

import edu.wpi.first.wpilibj.DriverStation;


public class StrafeToSwitch extends DriveDeltaXY {

	double strafeDistance;
	
	public StrafeToSwitch(double distance) {
		strafeDistance = distance;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
		  if(gameData.charAt(0) == 'L')
		  {
			deltaY = - strafeDistance;
		  } else {
			deltaY = strafeDistance;
		  }
        }
        
        deltaX = 0.0;
        super.initialize();
    }

}
