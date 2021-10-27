/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  private final NetworkTable table = NetworkTableInstance.getDefault().getTable("vision");

  public VisionSubsystem() {}

  public double getDistance() {
    return table.getEntry("distance").getDouble(0);
  }

  public double getAngle() {
    return table.getEntry("angle").getDouble(0);
  }
}
