/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;

public class RunShooterPreset extends RunShooter {

    protected double topShooterSpeed, bottomShooterSpeed;

    public RunShooterPreset(
            Shooter shooter, int bottomShooterSpeed, int topShooterSpeed) {
        super(shooter);
        this.bottomShooterSpeed = bottomShooterSpeed;
        this.topShooterSpeed = topShooterSpeed;
    }

    @Override
    public void execute() {
        shooter.run(bottomShooterSpeed, topShooterSpeed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
