package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.auto.FollowTrajectoryCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakePathFollowingCommand extends ParallelDeadlineGroup {

    public IntakePathFollowingCommand(DriveTrainSubsystem driveTrain, IntakeSubsystem intake, String pathname) {
        super(
                new FollowTrajectoryCommand(driveTrain, PathPlanner.loadPath(pathname, 4, 1)),
                new IntakeCommand(intake, IntakeSubsystem.Direction.In)
        );

    }
}