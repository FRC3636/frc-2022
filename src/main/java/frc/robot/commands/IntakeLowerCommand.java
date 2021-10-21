/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeLowerCommand extends CommandBase {

  private final IntakeSubsystem intake;
  private final Timer timer = new Timer();

  public IntakeLowerCommand(IntakeSubsystem intake) {
    this.intake = intake;
  }

  @Override
  public void initialize() {
    intake.setMotorSpeed(1);
    timer.reset();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(2);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stop();
  }
}
