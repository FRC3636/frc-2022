package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants.Drivetrain;
import frc.robot.subsystems.DriveTrainSubsystem;
import java.awt.*;


public class DriveDistanceCommand extends CommandBase {

  PIDController controller = new PIDController(0.01, 0.001, 0.001);
  private final DriveTrainSubsystem driveTrain;
  private final Double distance;

  public DriveDistanceCommand(DriveTrainSubsystem driveTrain, Double distance) {
    this.driveTrain = driveTrain;
    this.distance = distance;
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {
    driveTrain.zeroEncoders();
  }

  @Override
  public void execute() {
//    driveTrain.getDistance();

    double value = controller.calculate(driveTrain.getDistance(), distance);

    System.out.println(value);

//    System.out.println(MathUtil.clamp(controller.calculate(driveTrain.getLeftEncoder(), distance), -1, 1));
    driveTrain.tankDrive(
        controller.calculate(driveTrain.getLeftEncoder(), distance),
        controller.calculate(driveTrain.getRightEncoder(), distance));
//    driveTrain.arcadeDrive(controller.calculate(driveTrain.getDistance(), distance), 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {

  }
}
