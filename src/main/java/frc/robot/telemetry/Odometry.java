package frc.robot.telemetry;

import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants.Telemetry;
import frc.robot.util.Vector2;

public class Odometry {

  private final Encoder leftEncoder;
  private final Encoder rightEncoder;

  private Vector2 currentPos = new Vector2(0, 0);
  private Vector2 lastWheelDist = new Vector2(0, 0);
  private double currentAngle = 0;

  public Odometry(Encoder leftEncoder, Encoder rightEncoder) {
    this.leftEncoder = leftEncoder;
    this.rightEncoder = rightEncoder;
  }

  public void updateOdometry() {
    Vector2 currentWheelDist = new Vector2(leftEncoder.getDistance(), rightEncoder.getDistance());

    Vector2 deltaDist = currentWheelDist.cpy().sub(lastWheelDist);

    double deltaAvgDist = (deltaDist.x + deltaDist.y) / 2;

    double deltaAngle = (deltaDist.x - deltaDist.y) / Telemetry.DRIVE_TRAIN_WIDTH;

    currentAngle += deltaAngle;
    currentAngle = currentAngle % (Math.PI * 2);

    currentPos.add(
        new Vector2(deltaAvgDist * Math.sin(currentAngle), deltaAvgDist * Math.cos(currentAngle)));

    lastWheelDist = currentWheelDist;
  }

  public void zero() {
    currentPos = new Vector2();
    lastWheelDist = new Vector2();
    currentAngle = 0;
    leftEncoder.reset();
    rightEncoder.reset();
  }

  public Vector2 getCurrentPos() {
    return currentPos;
  }

  public double getCurrentAngle() {
    return currentAngle;
  }
}
