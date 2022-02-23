package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.Constants;

public class FollowTrajectoryCommand extends RamseteCommand {
    private final DriveTrainSubsystem driveTrain;

    public FollowTrajectoryCommand(DriveTrainSubsystem driveTrain, Trajectory trajectory) {
        super(
            trajectory,
            driveTrain::getPose,
            new RamseteController(Constants.Drivetrain.RAMSETE_B, Constants.Drivetrain.RAMSETE_ZETA),
            new SimpleMotorFeedforward(
                Constants.Drivetrain.FEED_FORWARD_KS,
                Constants.Drivetrain.FEED_FORWARD_KV,
                Constants.Drivetrain.FEED_FORWARD_KA
            ),
            Constants.Drivetrain.KINEMATICS,
            driveTrain::getWheelSpeeds,
            new PIDController(Constants.Drivetrain.DRIVE_VELOCITY_KP, 0, 0),
            new PIDController(Constants.Drivetrain.DRIVE_VELOCITY_KP, 0, 0),
            driveTrain::tankDriveVolts,
            driveTrain
        );

        driveTrain.resetOdometry(trajectory.getInitialPose());

        this.driveTrain = driveTrain;
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.stop();
        super.end(interrupted);
    }
}
