package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class RunConveyorForSetTime extends CommandBase {

    private Timer timer;
    private final ConveyorSubsystem conveyor;
    private final ConveyorSubsystem.Direction direction;
    private final double delay;
    private final double duration;


    public RunConveyorForSetTime(ConveyorSubsystem conveyor, ConveyorSubsystem.Direction direction, double delay, double duration) {
        this.conveyor = conveyor;
        this.direction = direction;
        this.delay = delay;
        this.duration = duration;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
        if(timer.hasElapsed(delay)) {
            conveyor.run(direction);
        }
    }


    @Override
    public void end(boolean interrupted) {
        conveyor.stop();
    }

    @Override
    public boolean isFinished() {
        return(timer.hasElapsed(duration));
    }
}
