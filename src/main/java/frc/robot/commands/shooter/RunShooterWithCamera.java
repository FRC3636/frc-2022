/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.shooter;

import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Shooter;

public class RunShooterWithCamera extends RunShooterWithDistance {

    private final Camera camera;

    public RunShooterWithCamera(Shooter shooter, Camera camera) {
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
