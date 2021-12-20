package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Drivetrain;
import frc.robot.subsystems.DriveTrainSubsystem;
import java.awt.*;

public class AutoCommand extends CommandBase {
  private final DriveTrainSubsystem driveTrain;

  private static final float distance = 5.4f;

  public AutoCommand(DriveTrainSubsystem driveTrain) {
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
  }

  @Override
  public void execute() {
    driveTrain.arcadeDrive(0.5, 0);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(driveTrain.getPose().getY() - distance) < 0.2;
  }
}
