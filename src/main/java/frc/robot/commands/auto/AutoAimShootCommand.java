/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.PointAtGoalCommand;
import frc.robot.commands.shooter.RunShooterWithCameraCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoAimShootCommand extends ParallelDeadlineGroup {
    public AutoAimShootCommand(
            ShooterSubsystem shooter,
            ConveyorSubsystem conveyor,
            CameraSubsystem camera,
            DriveTrainSubsystem driveTrain) {
        super(
                new SequentialCommandGroup(
                        new PointAtGoalCommand(driveTrain, camera),
                        new RunConveyorForSetTime(conveyor, ConveyorSubsystem.Direction.Up, 1, 4)),
                new RunShooterWithCameraCommand(shooter, camera));
    }
}
