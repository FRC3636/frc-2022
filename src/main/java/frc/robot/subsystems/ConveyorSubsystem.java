package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {

    private final CANSparkMax conveyorMotor;

    public ConveyorSubsystem() {
        conveyorMotor = new CANSparkMax(Constants.Conveyor.MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        conveyorMotor.setSmartCurrentLimit(20);
    }

    public void run(Direction direction) {
        conveyorMotor.set(direction == Direction.Up ? 1 : -1);
    }

    public void stop() {
        conveyorMotor.set(0);
    }

    public enum Direction {
        Up,
        Down
    }
}
