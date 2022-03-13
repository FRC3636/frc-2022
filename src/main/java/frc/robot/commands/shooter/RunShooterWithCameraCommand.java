/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.shooter;

import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterWithCameraCommand extends RunShooterWithDistanceCommand {

    private final CameraSubsystem camera;

    public RunShooterWithCameraCommand(ShooterSubsystem shooter, CameraSubsystem camera) {
        super(shooter, 0);
        this.camera = camera;
        addRequirements(camera);
    }

    @Override
    public void initialize() {
        camera.turnOnLight();
    }

    @Override
    public void execute() {
        distance = camera.getDistanceToGoal();
        super.execute();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        camera.turnOffLight();
    }
}
