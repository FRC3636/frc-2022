package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Conveyor;

public class AutoIndex extends Command {

    Conveyor conveyor;

    public AutoIndex(Conveyor conveyor) {

        this.conveyor = conveyor;
        addRequirements(conveyor);
    }

    @Override
    public void execute() {
        if(!conveyor.getBeamBreak()) {
            conveyor.run(Conveyor.Direction.Up);
        }
        else {
            conveyor.stop();
        }
    }
}
