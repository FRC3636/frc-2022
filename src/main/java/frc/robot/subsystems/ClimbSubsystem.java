/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {

  private final PowerDistributionPanel powerDist = new PowerDistributionPanel();
  private final Spark climb = new Spark(Constants.Climb.CLIMB_MOTOR);
  private final PWMVictorSPX brake = new PWMVictorSPX(Constants.Climb.BRAKE_MOTOR);

  private BrakeState brakeState = BrakeState.Transitioning;
  private BrakeState brakeTarget = BrakeState.Engaged;
  private int brakeTransitionTimer;

  public ClimbSubsystem() {
    brake.setInverted(true);
  }

  @Override
  public void periodic() {
    brakeTransitionTimer++;

    if ((brakeTarget.equals(BrakeState.Engaged)
            && powerDist.getCurrent(Constants.Climb.BRAKE_POWER)
                >= Constants.Climb.BRAKE_OVERDRAW_THRESHOLD)
        || (brakeTarget.equals(BrakeState.Released)
            && brakeTransitionTimer > (int) (Constants.Climb.BRAKE_TIMEOUT * 50))) {
      brake.stopMotor();
      brakeState = brakeTarget;
    }
  }

  public void extend(double magnitude) {
    if (brakeState.equals(BrakeState.Released)) {
      climb.set(magnitude);
    }
  }

  public void stop() {
    climb.set(0);
  }

  public BrakeState getBrakeState() {
    return brakeState;
  }

  /**
   * This function never blocks, instead setting the brake state to transitioning until the
   * transition is finished.
   */
  public void setBrakeTarget(BrakeState target) {
    brakeTarget = target;
    brakeState = BrakeState.Transitioning;
    brakeTransitionTimer = 0;
    // The brake motor is unset when the current draw increases beyond a certain threshold.
    switch (target) {
      case Engaged:
        brake.set(Constants.Climb.BRAKE_SPEED);
        break;
      case Released:
        brake.set(-Constants.Climb.BRAKE_SPEED);
        break;
    }
  }

  public enum BrakeState {
    Engaged,
    Released,
    // Brake transitioning or state unknown
    Transitioning,
  }
}
