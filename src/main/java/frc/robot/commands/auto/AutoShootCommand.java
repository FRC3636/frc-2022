package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.RunShooterPresetCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShootCommand extends ParallelDeadlineGroup {
    public AutoShootCommand(ShooterSubsystem shooter, ConveyorSubsystem conveyor) {
        super(
                new RunConveyorForSetTime(conveyor, ConveyorSubsystem.Direction.Up, 1, 5),
                new RunShooterPresetCommand(shooter, 3200, 500)
        );
    }
}
