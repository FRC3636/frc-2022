/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;

public class RunConveyorCommand extends CommandBase {

    protected final ConveyorSubsystem conveyor;
    protected final ConveyorSubsystem.Direction direction;

    public RunConveyorCommand(ConveyorSubsystem conveyor, ConveyorSubsystem.Direction direction) {
        this.conveyor = conveyor;
        this.direction = direction;

        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        conveyor.disableAutoIndex();
    }

    @Override
    public void execute() {
        conveyor.run(direction);
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.enableAutoIndex();
        conveyor.stop();
    }
}
