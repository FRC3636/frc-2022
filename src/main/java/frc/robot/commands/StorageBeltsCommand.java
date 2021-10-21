/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.StorageBeltsSubsystem;

public class StorageBeltsCommand extends CommandBase {

  private final StorageBeltsSubsystem storageBeltsSubsystem;

  public StorageBeltsCommand(StorageBeltsSubsystem storageBeltsSubsystem) {
    this.storageBeltsSubsystem = storageBeltsSubsystem;
    // each subsystem used by the command must be passed into the addRequirements() method (which
    // takes a vararg of Subsystem)
    addRequirements(this.storageBeltsSubsystem);
  }

  @Override
  public void initialize() {
    storageBeltsSubsystem.runBelts();
  }

  @Override
  public void end(boolean interrupted) {
    storageBeltsSubsystem.stopBelts();
  }
}
