/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class RunShooter extends Command {

    protected final Shooter shooter;

    public RunShooter(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
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
