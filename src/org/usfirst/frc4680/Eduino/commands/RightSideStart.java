package org.usfirst.frc4680.Eduino.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightSideStart extends CommandGroup {

    public RightSideStart() {
		addSequential(new FlipWrist(),2);
		//causes the wrist to flop down after moving forwards then backwards
		addSequential(new LongArmPosition(LongArmPosition.SWITCH_HEIGHT), 3);
		addSequential(new DriveABC('R'),5);
		addSequential(new SetHeight('R'),2);
		addSequential(new TurnTo(-90), 3.0);
		addSequential(new DriveDeltaXY(10, 0), 2.0);
		addSequential(new Score('R'),2);
		addSequential(new DriveDeltaXY(-15,0));
    }
}
