/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {

  private final IntakeSubsystem intakeSubsystem;
  private final boolean intake;

  public IntakeCommand(IntakeSubsystem intakeSubsystem, boolean intake) {
    this.intakeSubsystem = intakeSubsystem;
    this.intake = intake;
    addRequirements(this.intakeSubsystem);
  }

  @Override
  public void initialize() {
    intakeSubsystem.setRunning(intake);
  }

  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.stop();
  }
}
