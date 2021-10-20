package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;


public class ClimbCommand extends CommandBase {
  private final ClimbSubsystem climbSubsystem;

  public ClimbCommand(ClimbSubsystem climbSubsystem) {
    this.climbSubsystem = climbSubsystem;
    addRequirements(this.climbSubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    climbSubsystem.extend(RobotContainer.controller.getY() * 0.5);
  }

  @Override
  public void end(boolean interrupted) {
    climbSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
