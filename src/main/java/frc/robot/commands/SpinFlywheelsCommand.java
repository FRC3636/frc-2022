/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class SpinFlywheelsCommand extends CommandBase {

  private final ShooterSubsystem shooterSubsystem;
  private final double speed;

  public SpinFlywheelsCommand(ShooterSubsystem shooterSubsystem, double speed) {
    this.speed = speed;
    this.shooterSubsystem = shooterSubsystem;
    addRequirements(this.shooterSubsystem);
  }

  @Override
  public void initialize() {
    shooterSubsystem.spin(speed);
  }

  @Override
  public void end(boolean interrupted) {
    shooterSubsystem.spin(0);
  }
}
