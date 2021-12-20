package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WaterGun;

public class WaterGunSubsystem extends SubsystemBase {
  private final Solenoid solenoid;

  public WaterGunSubsystem() {
    solenoid = new Solenoid(WaterGun.CHANNEL);
  }

  public void open() {
    solenoid.set(true);
  }

  public void close() {
    solenoid.set(false);
  }
}
