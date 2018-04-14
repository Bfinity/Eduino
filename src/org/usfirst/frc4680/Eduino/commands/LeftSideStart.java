package org.usfirst.frc4680.Eduino.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftSideStart extends CommandGroup {

    public LeftSideStart() {
    			addSequential(new FlipWrist(),2);
    			//causes the wrist to flop down after moving forwards then backwards
    			addSequential(new LongArmPosition(LongArmPosition.SWITCH_HEIGHT), 3);
    			addSequential(new DriveABC('L'),5);
    			addSequential(new SetHeight('L'),2);
    			addSequential(new TurnTo(90), 3.0);
    			addSequential(new DriveDeltaXY(10, 0),2);
    			addSequential(new Score('L'),2);
    			addSequential(new DriveDeltaXY(-15,0));
    	}
}
