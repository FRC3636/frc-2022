/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.StorageBeltsSubsystem;
import frc.robot.subsystems.StorageBeltsSubsystem.Direction;

public class StorageBeltsCommand extends CommandBase {

  private final StorageBeltsSubsystem storageBelts;
  private final Direction direction;

  public StorageBeltsCommand(StorageBeltsSubsystem storageBelts, Direction direction) {
    this.storageBelts = storageBelts;
    addRequirements(this.storageBelts);

    this.direction = direction;
  }

  @Override
  public void initialize() {
    storageBelts.runBelts(direction);
  }

  @Override
  public void end(boolean interrupted) {
    storageBelts.stopBelts();
  }
}
