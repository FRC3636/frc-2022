// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTableSubsystem extends SubsystemBase {
  NetworkTableEntry angleEntry;
  NetworkTableEntry distanceEntry;

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("SmartDashboard");

  public void teleopPeriodic() {
    angleEntry = table.getEntry("Angle");
    distanceEntry = table.getEntry("Distance");

    System.out.println("angle: " + angleEntry);
    System.out.println("distance: " + distanceEntry);
  }
}

