package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterWithVisionCommand extends CommandBase {
    private final ShooterSubsystem shooter;

    private final CameraSubsystem camera;

    public RunShooterWithVisionCommand(ShooterSubsystem shooter, NetworkTableEntry bottomShooterSpeed, NetworkTableEntry topShooterSpeed, CameraSubsystem camera) {
        this.shooter = shooter;
        this.camera = camera;
        addRequirements(shooter, camera);
        camera.turnOnLight();
    }

    @Override
    public void execute() {
        double distance = Units.metersToFeet(camera.getDistanceToGoal());

        shooter.run(getBottomSpeedFromDist(distance), getTopSpeedFromDist(distance));

        System.out.println(camera.getDistanceToGoal());
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
        camera.turnOffLight();
    }
}
