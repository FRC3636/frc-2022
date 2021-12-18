package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Intake;

public class IntakeSubsystem extends SubsystemBase {
  private final Spark motor = new Spark(Intake.MOTOR);

  public IntakeSubsystem() {}

  public void setRunning(Direction direction) {
    motor.set(direction == Direction.IN ? 1 : -1);
  }

  public void stop() {
    motor.set(0);
  }

  public enum Direction {
    IN,
    OUT
  }
}
