/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {

  private final IntakeSubsystem intake;
  private final IntakeSubsystem.Direction direction;

  public IntakeCommand(IntakeSubsystem intake, IntakeSubsystem.Direction direction) {
    this.intake = intake;
    this.direction = direction;

    addRequirements(this.intake);
  }

  @Override
  public void initialize() {
    intake.setRunning(direction);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stop();
  }
}
