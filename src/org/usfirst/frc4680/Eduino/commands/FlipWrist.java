package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlipWrist extends DriveDeltaXY {
    protected void initialize() {
        deltaX = 14;
        deltaY = 0;
        
        super.initialize();
    }
}
