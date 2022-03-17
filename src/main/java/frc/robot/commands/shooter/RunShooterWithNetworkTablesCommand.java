package frc.robot.commands.shooter;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterWithNetworkTablesCommand extends RunShooterPresetCommand{

    public NetworkTableEntry bottomShooterSpeedEntry, topShooterSpeedEntry;

    public RunShooterWithNetworkTablesCommand(
            ShooterSubsystem shooter, NetworkTableEntry bottomShooterSpeedEntry, NetworkTableEntry topShooterSpeedEntry) {
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
