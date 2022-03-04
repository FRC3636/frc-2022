package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Shooter;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterPresetCommand extends CommandBase {

    private final ShooterSubsystem shooter;

    int topShooterSpeed, bottomShooterSpeed;

    public RunShooterPresetCommand(ShooterSubsystem shooter, int bottomShooterSpeed, int topShooterSpeed) {
        this.shooter = shooter;
        addRequirements(shooter);
        this.bottomShooterSpeed = bottomShooterSpeed;
        this.topShooterSpeed = topShooterSpeed;
    }

    @Override
    public void execute() {
        shooter.run(bottomShooterSpeed, topShooterSpeed);
    }

    @Override
    public boolean isFinished() {
//        return Math.abs(shooter.getVelocity()[0] - bottomShooterSpeed) < 50 && Math.abs(shooter.getVelocity()[1] - topShooterSpeed) < 50;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}