package frc.robot.telemetry;

import edu.wpi.first.wpilibj.Encoder;

public class Odometry {

  private Encoder leftEncoder;
  private Encoder rightEncoder;

  public Odometry(Encoder leftEncoder, Encoder rightEncoder) {
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
  }
}
