package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.RunConveyorCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoRunConveyorCommand extends RunConveyorCommand {

    private ShooterSubsystem shooter;
    private CameraSubsystem camera;

    private Timer timer;

    public AutoRunConveyorCommand(ConveyorSubsystem conveyor, ShooterSubsystem shooter, CameraSubsystem camera) {
        super(conveyor, ConveyorSubsystem.Direction.Up);
        this.shooter = shooter;
        this.camera = camera;

        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        super.initialize();
        timer = new Timer();
    }

    @Override
    public void execute() {
        if(shooter.atSetSpeed() && Math.abs(camera.getAngleToGoalDegrees()) < 2) {
            conveyor.run(direction);
            timer.start();
        }
        else {
            conveyor.stop();
            timer.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(2);
    }
}
