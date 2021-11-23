package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;

public class FollowTrajectoryCommand extends RamseteCommand {
  public FollowTrajectoryCommand(DriveTrainSubsystem driveTrain, Trajectory trajectory) {
    super(
      trajectory, 
      driveTrain::getPose, 
      new RamseteController(Constants.Drivetrain.RAMSETE_B, Constants.Drivetrain.RAMSETE_ZETA), 
      Constants.Drivetrain.KINEMATICS,
      driveTrain::tankDriveVolts,
      driveTrain
    );
  } 
}
