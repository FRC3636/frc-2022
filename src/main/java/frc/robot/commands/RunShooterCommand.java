package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterCommand extends CommandBase {

    private final ShooterSubsystem shooter;
    private final CameraSubsystem camera;

    public RunShooterCommand(ShooterSubsystem shooter, CameraSubsystem camera) {
        this.shooter = shooter;
        this.camera = camera;
        addRequirements(shooter, camera);
    }

    @Override
    public void initialize() {
        camera.turnOnLight();
    }

    @Override
    public void execute() {
        shooter.run(
            getBottomSpeedFromDist(camera.getDistanceToGoal()),
            getTopSpeedFromDist(camera.getDistanceToGoal())
        );
        RobotContainer.angleToGoal.setDouble(camera.getAngleToGoalDegrees());
    }

    public double getBottomSpeedFromDist(Double distance) {
        return (-355.5 * distance) + 3200;
    }

    public double getTopSpeedFromDist(Double distance) {
        double speed = (1200 * Math.pow(distance, 1.5)) + 500;
        return speed > 5700 ? 5700 : speed;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        camera.turnOffLight();
    }
}
