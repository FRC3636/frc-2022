package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.shooter.RunShooterWithCameraCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoPathFollowing extends ParallelDeadlineGroup {

    public AutoPathFollowing(DriveTrainSubsystem driveTrain, IntakeSubsystem intake, ShooterSubsystem shooter, CameraSubsystem camera, String pathname, boolean resetOdometry) {
        super(
                new FollowTrajectoryCommand(driveTrain, PathPlanner.loadPath(pathname, 5, 1.4), resetOdometry),
                new IntakeCommand(intake, 1),
                new RunShooterWithCameraCommand(shooter, camera)
        );

    }
}
