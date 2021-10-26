/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

  private final VictorSP motor = new VictorSP(Constants.Intake.MOTOR);

  public IntakeSubsystem() {
    motor.setInverted(true);
  }

  public void setRunning(Direction direction) {
    motor.set((direction == Direction.In ? 1 : -1) * 0.7);
  }

  public void setMotorSpeed(double speed) {
    motor.set(speed);
  }

  public void stop() {
    motor.set(0);
  }

  public enum Direction {
    In,
    Out,
  }
}
