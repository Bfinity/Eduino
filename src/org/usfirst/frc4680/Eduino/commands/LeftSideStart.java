package org.usfirst.frc4680.Eduino.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftSideStart extends CommandGroup {

    public LeftSideStart() {
    			addSequential(new FlipWrist());
    			//causes the wrist to flop down after moving forwards then backwards
    			addSequential(new LongArmPosition(LongArmPosition.SWITCH_HEIGHT), 3);
    			addSequential(new DriveABC('L'));
    			addSequential(new SetHeight('L'));
    			addSequential(new TurnTo(90), 3.0);
    			addSequential(new DriveDeltaXY(10, 0));
    			addSequential(new Score('L'));
    			addSequential(new DriveDeltaXY(-10,0));
    	}
}
