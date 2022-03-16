/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {
    private final CANSparkMax conveyorMotor;

    DigitalInput beamBreak = new DigitalInput(1);

    private AutoIndex autoIndex = AutoIndex.Enabled;

    public ConveyorSubsystem() {
        conveyorMotor =
                new CANSparkMax(Constants.Conveyor.MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
                conveyorMotor.setSmartCurrentLimit(20);
    }

    public void run(Direction direction) {
        conveyorMotor.set(direction == Direction.Up ? 1 : -1);
    }

    public void stop() {
        conveyorMotor.set(0);
    }

    @Override
    public void periodic() {
        if (this.autoIndex == AutoIndex.Enabled) {
            run(ConveyorSubsystem.Direction.Up);
        }
    }

    public void enableAutoIndex() {
        if(autoIndex == AutoIndex.Stopped) {
            this.autoIndex = AutoIndex.Enabled;
        }
    }

    public void stopAutoIndex() {
        this.autoIndex = AutoIndex.Stopped;
    }

    public void disableAutoIndex() {
        if(autoIndex == AutoIndex.Enabled) {
            this.autoIndex = AutoIndex.Disabled;
        }
    }

    public void runAutoIndex() {
        this.autoIndex = AutoIndex.Enabled;
    }
    public enum Direction {
        Up,
        Down
    }

    public enum AutoIndex {
        Enabled,
        Disabled,
        Stopped
    }
}
