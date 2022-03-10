package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.RunShooterCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShootCommand extends ParallelDeadlineGroup {
    public AutoShootCommand(ShooterSubsystem shooter, ConveyorSubsystem conveyor, CameraSubsystem camera) {
        super(
                new RunConveyorForSetTime(conveyor, ConveyorSubsystem.Direction.Up, 1, 3),
                new RunShooterCommand(shooter, camera)
        );
    }
}
