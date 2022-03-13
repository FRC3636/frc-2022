/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.shooter;

import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterPresetCommand extends RunShooterCommand {

    int topShooterSpeed, bottomShooterSpeed;

    public RunShooterPresetCommand(
            ShooterSubsystem shooter, int bottomShooterSpeed, int topShooterSpeed) {
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
