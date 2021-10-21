/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class SpinFlywheelsCommand extends CommandBase {

  private final ShooterSubsystem shooter;
  private final double speed;

  public SpinFlywheelsCommand(ShooterSubsystem shooter, double speed) {
    this.shooter = shooter;
    this.speed = speed;
    addRequirements(this.shooter);
  }

  @Override
  public void initialize() {
    shooter.spin(speed);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.spin(0);
  }
}
