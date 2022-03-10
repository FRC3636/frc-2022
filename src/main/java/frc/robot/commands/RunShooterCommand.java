package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterCommand extends CommandBase {

    NetworkTableEntry topShooterSpeed, bottomShooterSpeed;

    private final ShooterSubsystem shooter;

    public RunShooterCommand(ShooterSubsystem shooter, NetworkTableEntry bottomShooterSpeed, NetworkTableEntry topShooterSpeed) {
        this.shooter = shooter;
        addRequirements(shooter);
        this.bottomShooterSpeed = bottomShooterSpeed;
        this.topShooterSpeed = topShooterSpeed;
    }

    @Override
    public void execute() {
        shooter.run(
                bottomShooterSpeed.getDouble(0),
                topShooterSpeed.getDouble(0));
    }

    public double getBottomSpeedFromDist(Double distance) {
        return ((-1000/9.0) * distance) + 3200;
    }

    public double getTopSpeedFromDist(Double distance) {
        double speed = (200 * Math.pow(distance, 1.5)) + 500;
        return speed > 5700 ? 5700 : speed;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
