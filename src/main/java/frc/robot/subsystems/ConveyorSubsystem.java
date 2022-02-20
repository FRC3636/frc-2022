package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {

    private final Spark conveyorMotor;

    public ConveyorSubsystem() {
        conveyorMotor = new Spark(Constants.Conveyor.MOTOR);
    }

    public void run(Direction direction) {
        conveyorMotor.set(direction == Direction.Up ? -1 : 1);
    }

    public void stop() {
        conveyorMotor.set(0);
    }

    public enum Direction {
        Up,
        Down
    }
}
