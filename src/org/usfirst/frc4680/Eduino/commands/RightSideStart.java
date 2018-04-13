package org.usfirst.frc4680.Eduino.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightSideStart extends CommandGroup {

    public RightSideStart() {
		addSequential(new FlipWrist());
		//causes the wrist to flop down after moving forwards then backwards
		addSequential(new LongArmPosition(LongArmPosition.SWITCH_HEIGHT), 3);
		addSequential(new DriveABC('R'));
		addSequential(new SetHeight('R'));
		addSequential(new TurnTo(-90), 3.0);
		addSequential(new DriveDeltaXY(10, 0));
		addSequential(new Score('R'));
		addSequential(new DriveDeltaXY(-15,0));
    }
}
