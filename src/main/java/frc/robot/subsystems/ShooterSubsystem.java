/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

  PWMVictorSPX motorLeft = new PWMVictorSPX(Constants.Shooter.MOTOR_LEFT);
  PWMVictorSPX motorRight = new PWMVictorSPX(Constants.Shooter.MOTOR_RIGHT);

  public ShooterSubsystem() {
    motorRight.setInverted(true);
  }

  public void spin(double speed) {
    motorLeft.set(speed);
    motorRight.set(speed);
  }
}
