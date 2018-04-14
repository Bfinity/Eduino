package org.usfirst.frc4680.Eduino.commands;

import org.usfirst.frc4680.Eduino.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlipWrist extends DriveDeltaXY {
    protected void initialize() {
        deltaX = 15;
        deltaY = 0;
        rampDownDistanceFB = 12;
        rampUpTimeFB = 0.25;
        super.initialize();
    }
}
