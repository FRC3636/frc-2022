package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;


public class UnloadConveyor extends CommandBase {

    private final Shooter shooter;
    private final Camera camera;
    private final Conveyor conveyor;

    private boolean shooterReady;
    private boolean conveyorRunning;

    public UnloadConveyor(Conveyor conveyor, Shooter shooter, Camera camera) {
        this.shooter = shooter;
        this.camera = camera;
        this.conveyor = conveyor;

        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        super.initialize();

        shooterReady = false;
        conveyorRunning = false;
    }

    @Override
    public void execute() {
        if (shooterReady && camera.underThreshold()) {
            conveyor.run(Conveyor.Direction.Up);
        }
        if (conveyor.getCurrent() > frc.robot.Constants.Conveyor.CURRENT_THRESHOLD) {
            conveyorRunning = true;
        }
        if (shooter.atSetSpeed()) {
            shooterReady = true;
        }
    }

    @Override
    public boolean isFinished() {
        return shooterReady && conveyorRunning && conveyor.getCurrent() < frc.robot.Constants.Conveyor.CURRENT_THRESHOLD && camera.underThreshold();
    }
}
