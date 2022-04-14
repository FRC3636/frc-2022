package frc.robot.commands.shooter;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.Shooter;

public class RunShooterWithNetworkTables extends RunShooterPreset {

    public NetworkTableEntry bottomShooterSpeedEntry, topShooterSpeedEntry;

    public RunShooterWithNetworkTables(
            Shooter shooter, NetworkTableEntry bottomShooterSpeedEntry, NetworkTableEntry topShooterSpeedEntry) {
        super(shooter, 0, 0);
        this.bottomShooterSpeedEntry = bottomShooterSpeedEntry;
        this.topShooterSpeedEntry = topShooterSpeedEntry;
    }

    @Override
    public void execute() {
        this.topShooterSpeed = topShooterSpeedEntry.getDouble(0);
        this.bottomShooterSpeed = bottomShooterSpeedEntry.getDouble(0);
        super.execute();
    }
}
