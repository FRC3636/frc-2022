/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase {

  private final ClimbSubsystem climb;

  public ClimbCommand(ClimbSubsystem climb) {
    this.climb = climb;
    addRequirements(this.climb);
  }

  @Override
  public void execute() {
    climb.extend(RobotContainer.controller.getY() * 0.5);
  }

  @Override
  public void end(boolean interrupted) {
    climb.stop();
  }
}
