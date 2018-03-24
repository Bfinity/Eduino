package org.usfirst.frc4680.Eduino.commands;

/**
 *
 */
public class BetterCrossLineAndStop extends DriveDeltaXY {

    
    protected void initialize() {
    	deltaX = 100;
    	deltaY = 0;
    
    	super.initialize();
    }
}
