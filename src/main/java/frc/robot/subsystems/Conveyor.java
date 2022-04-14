/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Conveyor extends SubsystemBase {
    private final CANSparkMax conveyorMotor;
    private final DigitalInput beamBreak = new DigitalInput(Constants.Conveyor.BEAM_BREAK);

    private AutoIndex autoIndex = AutoIndex.Enabled;

    public Conveyor() {
        conveyorMotor = new CANSparkMax(Constants.Conveyor.MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        conveyorMotor.setSmartCurrentLimit(20);
    }

    public void run(Direction direction) {
        conveyorMotor.set(direction == Direction.Up ? 0.75 : -0.75);
    }
    public void stop() {
        conveyorMotor.set(0);
    }

    @Override
    public void periodic() {
        if (this.autoIndex == AutoIndex.Enabled) {
            if(!beamBreak.get()) {
                run(Conveyor.Direction.Up);
            }
            else {
                stop();
            }
        }
    }

    public void enableAutoIndex() {
        if(autoIndex == AutoIndex.Disabled) {
            this.autoIndex = AutoIndex.Enabled;
        }
    }

    public void disableAutoIndex() {
        if(autoIndex == AutoIndex.Enabled) {
            this.autoIndex = AutoIndex.Disabled;
        }
    }

    public void toggleAutoIndex() {
        this.autoIndex = autoIndex == AutoIndex.Enabled ? AutoIndex.Stopped : AutoIndex.Enabled;
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

    public boolean getBeamBreak() {
        return beamBreak.get();
    }

    public double getCurrent() {
        return conveyorMotor.getOutputCurrent();
    }
}
