package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.CircularBuffer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
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
        super.initialize();

//        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        shooterReady = false;
        conveyorRunning = false;
    }

    @Override
    public void execute() {
        if (shooterReady && camera.underThreshold()) {
            conveyor.run(Conveyor.Direction.Up);
        }

        if (conveyor.getCurrent() > Constants.Conveyor.CURRENT_THRESHOLD) {
            conveyorRunning = true;
        }

        if (shooter.atSetpoint()) {
            shooterReady = true;
        }
    }

    @Override
    public boolean isFinished() {
        return shooterReady && conveyorRunning && conveyor.getCurrent() < frc.robot.Constants.Conveyor.CURRENT_THRESHOLD;
    }
}
