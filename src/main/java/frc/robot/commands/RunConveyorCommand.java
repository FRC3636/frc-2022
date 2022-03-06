package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class RunConveyorCommand extends CommandBase {

    private final ConveyorSubsystem conveyor;
    private final ConveyorSubsystem.Direction direction;

    public RunConveyorCommand(ConveyorSubsystem conveyor, ConveyorSubsystem.Direction direction) {
        this.conveyor = conveyor;
        this.direction = direction;

        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        conveyor.run(direction);
        conveyor.disableAutoIndex();
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stop();
        conveyor.enableAutoIndexing();
    }
}
