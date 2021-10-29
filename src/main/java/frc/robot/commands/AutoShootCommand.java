/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.Autonomous;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import java.util.ArrayList;

public class AutoShootCommand extends CommandBase {

  private final DriveTrainSubsystem driveTrain;
  private final VisionSubsystem vision;
  private final ArrayList<double[]> lastReadings = new ArrayList<>();

  public AutoShootCommand(DriveTrainSubsystem driveTrain, VisionSubsystem vision) {
    this.driveTrain = driveTrain;
    this.vision = vision;
    addRequirements(driveTrain, vision);
  }

  public void execute() {
    // Store Last angle and distance values
    if (lastReadings.size() > Constants.Autonomous.READING_LENGTH) {
      lastReadings.remove(0);
    }
    lastReadings.add(new double[]{vision.getDistance(), vision.getAngle()});

    if (vision.getDistance() != 0) {
      driveTrain.arcadeDrive(
          distanceResponseFactor(vision.getDistance() - Autonomous.SHOOT_DIST),
          angleResponseFactor(vision.getAngle()));
    }
  }

  public boolean isFinished() {
//    return (Math.abs(vision.getAngle()) < Constants.Autonomous.ANGLE_ERROR
//        && Math.abs(vision.getDistance() - Constants.Autonomous.SHOOT_DIST)
//            < Constants.Autonomous.SHOOT_DIST_ERROR);
    // get the average distance and angle from the readings list
    int distMean = 0;
    int angleMean = 0;
    for (double[] reading : lastReadings) {
      distMean += reading[0];
      angleMean += reading[1];
    }
    distMean = distMean / lastReadings.size();
    angleMean = angleMean / lastReadings.size();

    // get the variance of the readings  Formula: s^2 = Sum(list - listMean) / lengthList
    int distVariance = 0;
    int angleVariance = 0;
    for (double[] reading : lastReadings) {
      distVariance += Math.pow(reading[0] - distMean, 2);
      angleVariance += Math.pow(reading[1] - angleMean, 2);
    }
    distVariance = distVariance / lastReadings.size();
    angleVariance = angleVariance / lastReadings.size();

    // note the variance is the square of the average distance from the mean
    return (Math.sqrt(distVariance) <= Autonomous.SHOOT_DIST_CHANGE_ERROR) && (
        Math.sqrt(angleVariance) <= Autonomous.ANGLE_CHANGE_ERROR)
        && Math.abs(vision.getDistance() - Autonomous.SHOOT_DIST) <= Autonomous.SHOOT_DIST_ERROR
        && Math.abs(vision.getAngle()) <= Autonomous.ANGLE_ERROR;
  }

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
