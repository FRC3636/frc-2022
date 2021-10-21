/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.StorageBeltsSubsystem;

public class StorageBeltsCommand extends CommandBase {

  private final StorageBeltsSubsystem storageBelts;

  public StorageBeltsCommand(StorageBeltsSubsystem storageBelts) {
    this.storageBelts = storageBelts;
    addRequirements(this.storageBelts);
  }

  @Override
  public void initialize() {
    storageBelts.runBelts();
  }

  @Override
  public void end(boolean interrupted) {
    storageBelts.stopBelts();
  }
}
