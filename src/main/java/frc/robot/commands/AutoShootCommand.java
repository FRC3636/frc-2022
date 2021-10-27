/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutoShootCommand extends CommandBase {
  private final DriveTrainSubsystem driveTrain;
  private final VisionSubsystem vision;

  public AutoShootCommand(DriveTrainSubsystem driveTrain, VisionSubsystem vision) {
    this.driveTrain = driveTrain;
    this.vision = vision;
    addRequirements(driveTrain, vision);
  }

  public void periodic() {
    driveTrain.arcadeDrive(
        responseFactor(vision.getDistance()), responseFactor(vision.getAngle() / 360) * 2);
  }

  public boolean isFinished() {
    return (Math.abs(vision.getAngle()) < Constants.Autonomous.ANGLE_ERROR
        && Math.abs(vision.getDistance() - Constants.Autonomous.SHOOT_DIST)
            < Constants.Autonomous.SHOOT_DIST_ERROR);
  }

  public void end() {
    driveTrain.arcadeDrive(0, 0);
  }

  private double responseFactor(double delta) {
    return (delta >= 0 ? -1 : 1) / (Math.pow(delta, 2) + 1) + 1;
  }
}
