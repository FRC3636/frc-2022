/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {
    private final CANSparkMax conveyorMotor;
    public double goal;
    DigitalInput beamBreak = new DigitalInput(1);

    private AutoIndex autoIndex = AutoIndex.Enabled;

    public ConveyorSubsystem() {
        conveyorMotor =
                new CANSparkMax(Constants.Conveyor.MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
                conveyorMotor.setSmartCurrentLimit(20);
    }

    public void run(Direction direction) {
        //conveyorMotor.set(direction == Direction.Up ? 1 : -1);
        goal = (direction == Direction.Up ? 1 : -1);
    }
    public void stop() {
        conveyorMotor.set(0);
    }

    @Override
    public void periodic() {
        if(conveyorMotor.get() < goal){
            conveyorMotor.set(conveyorMotor.get()+.01);
        }
        if(conveyorMotor.get() > goal){
            conveyorMotor.set(conveyorMotor.get()-.01);
        }
        if (this.autoIndex == AutoIndex.Enabled) {
            if(!beamBreak.get()) {
                run(ConveyorSubsystem.Direction.Up);
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
}
