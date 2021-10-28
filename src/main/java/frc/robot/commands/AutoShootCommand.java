/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.Autonomous;
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

  public void execute() {
    System.out.println(distanceResponseFactor((vision.getDistance() - Autonomous.SHOOT_DIST) / 7));
    if(vision.getDistance() != 0) {
      driveTrain.arcadeDrive(
          distanceResponseFactor(vision.getDistance() - Autonomous.SHOOT_DIST), angleResponseFactor(vision.getAngle()));
    }
  }

//  public boolean isFinished() {
//    return (Math.abs(vision.getAngle()) < Constants.Autonomous.ANGLE_ERROR
//        && Math.abs(vision.getDistance() - Constants.Autonomous.SHOOT_DIST)
//            < Constants.Autonomous.SHOOT_DIST_ERROR);
//  }

  public void end() {
    driveTrain.arcadeDrive(0, 0);
  }

  private double distanceResponseFactor(double delta) {
//    return (delta >= 0 ? -1 : 1) / (Math.pow(delta, 2) + 1) + 1;
    return (1.2 / (1 + Math.pow(1.2, -delta))) - 0.6;
  }

  private double angleResponseFactor(double delta) {
//    return (delta >= 0 ? -1 : 1) / (Math.pow(delta, 2) + 1) + 1;
    return (1.5 / (1 + Math.pow(1.4, -delta))) - 0.75;
  }
}
