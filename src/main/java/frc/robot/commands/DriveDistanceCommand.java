package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;


public class DriveDistanceCommand extends CommandBase {

  private final PIDController controller = new PIDController(0.2, 0.1, 0.02);
  private final DriveTrainSubsystem driveTrain;
  private final double distance;

  public DriveDistanceCommand(DriveTrainSubsystem driveTrain, double distance) {
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
    driveTrain.arcadeDrive(controller.calculate(driveTrain.getDistance(), distance), 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {

  }
}
