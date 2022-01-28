package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

import java.net.ContentHandler;

public class DriveConveyorCommand extends CommandBase {

    private final ConveyorSubsystem conveyor;
    private final ConveyorSubsystem.Direction direction;

    public DriveConveyorCommand(ConveyorSubsystem conveyor, ConveyorSubsystem.Direction direction) {
        this.conveyor = conveyor;
        this.direction = direction;

        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        conveyor.run(direction);
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stop();
    }
}
