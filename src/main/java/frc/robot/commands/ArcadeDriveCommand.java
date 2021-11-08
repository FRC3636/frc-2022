/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;

public class ArcadeDriveCommand extends CommandBase {

  private final DriveTrainSubsystem driveTrain;

  public ArcadeDriveCommand(DriveTrainSubsystem driveTrain) {
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {
    driveTrain.zeroEncoders();
  }

  @Override
  public void execute() {
    driveTrain.getDistance();
    double speed = RobotContainer.joystickLeft.getY();
    double turn = RobotContainer.joystickRight.getX();

//    driveTrain.arcadeDrive(speed, turn);

    driveTrain.arcadeDrive(speed, turn);
  }

  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
  }
}
