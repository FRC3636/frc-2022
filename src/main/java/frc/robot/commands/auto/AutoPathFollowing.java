package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.RunIntakeCommand;
import frc.robot.commands.shooter.RunShooterWithCamera;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoPathFollowing extends ParallelDeadlineGroup {

    public AutoPathFollowing(DriveTrain driveTrain, Intake intake, Shooter shooter, Camera camera, String pathname, boolean resetOdometry) {
        this(driveTrain, intake, shooter, camera, pathname, resetOdometry, 5,1.4);
    }

    public AutoPathFollowing(DriveTrain driveTrain, Intake intake, Shooter shooter, Camera camera, String pathname, boolean resetOdometry, double maxVel, double maxAccel) {
        super(
                new FollowTrajectoryCommand(driveTrain, PathPlanner.loadPath(pathname, maxVel, maxAccel), resetOdometry),
                new RunIntakeCommand(intake, 1),
                new RunShooterWithCamera(shooter, camera)
        );
    }
}
