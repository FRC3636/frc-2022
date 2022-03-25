/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.shooter.RunShooterWithDistanceCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShootFromDistanceCommand extends ParallelDeadlineGroup {
    public AutoShootFromDistanceCommand(
            ShooterSubsystem shooter,
            ConveyorSubsystem conveyor,
            DriveTrainSubsystem driveTrain,
            double distance) {
        super(
                new RunConveyorForSetTimeCommand(conveyor, ConveyorSubsystem.Direction.Up, 1, 4),
                new RunShooterWithDistanceCommand(shooter, distance));
    }
}
