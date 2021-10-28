/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class StorageBeltsSubsystem extends SubsystemBase {

  private final PWMVictorSPX beltMotor = new PWMVictorSPX(Constants.StorageBelts.BELT_MOTOR);

  public StorageBeltsSubsystem() {
  }

  public void runBelts(Direction direction) {
    beltMotor.set(direction == Direction.Forward ? 1 : -1);
  }

  public void stopBelts() {
    beltMotor.set(0);
  }

  public enum Direction {
    Forward,
    Backward
  }
}
