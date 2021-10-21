/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbBrakeCommand extends CommandBase {

  private final ClimbSubsystem climb;
  private final ClimbSubsystem.BrakeState target;

  public ClimbBrakeCommand(ClimbSubsystem climb, ClimbSubsystem.BrakeState target) {
    this.climb = climb;
    this.target = target;
    addRequirements(this.climb);
  }

  @Override
  public void initialize() {
    climb.setBrakeTarget(this.target);
  }

  @Override
  public boolean isFinished() {
    return climb.getBrakeState().equals(this.target);
  }
}
