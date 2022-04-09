package frc.robot.commands.auto;

import frc.robot.commands.RunConveyorCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import static frc.robot.Constants.*;


public class UnloadConveyorCommand extends RunConveyorCommand {

    private ShooterSubsystem shooter;
    private CameraSubsystem camera;

    private boolean shooterReady;
    private boolean conveyorRunning;

    public UnloadConveyorCommand(ConveyorSubsystem conveyor, ShooterSubsystem shooter, CameraSubsystem camera) {
        super(conveyor, ConveyorSubsystem.Direction.Up);
        this.shooter = shooter;
        this.camera = camera;

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
            conveyor.run(ConveyorSubsystem.Direction.Up);
        }
        if (conveyor.getCurrent() > Conveyor.CURRENT_THRESHOLD) {
            conveyorRunning = true;
        }
        if (shooter.atSetSpeed()) {
            shooterReady = true;
        }
    }

    @Override
    public boolean isFinished() {
        return shooterReady && conveyorRunning && conveyor.getCurrent() < Conveyor.CURRENT_THRESHOLD && camera.underThreshold();
    }
}
