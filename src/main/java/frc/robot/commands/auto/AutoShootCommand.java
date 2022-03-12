package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.PointAtGoalCommand;
import frc.robot.commands.RunShooterCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShootCommand extends ParallelDeadlineGroup {
    public AutoShootCommand(ShooterSubsystem shooter, ConveyorSubsystem conveyor, CameraSubsystem camera, DriveTrainSubsystem driveTrain) {
        super(
                new RunConveyorForSetTime(conveyor, ConveyorSubsystem.Direction.Up, 1,4),
                new RunShooterCommand(shooter, camera)
        );
    }
}
