package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageBeltsSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

  public AutoCommandGroup(DriveTrainSubsystem driveTrainSubsystem,
      VisionSubsystem visionSubsystem, ShooterSubsystem shooterSubsystem,
      StorageBeltsSubsystem storageBeltsSubsystem) {

    super(new AutoShootCommand(driveTrainSubsystem, visionSubsystem),
        new UnloadBallsCommand(shooterSubsystem, storageBeltsSubsystem));
  }
}