package frc.robot.subsystems;

import javax.sound.sampled.SourceDataLine;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {
    private final CANSparkMax conveyorMotor;
    DigitalInput beamBreakSensor = new DigitalInput(0);

    private boolean autoIndexing = true;
    
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

    public void disableAutoIndex() {
        autoIndexing = false;
    }

    public void enableAutoIndexing() {
        autoIndexing = true;
    }

    @Override
    public void periodic(){
        if (autoIndexing){
            if(!beamBreakSensor.get()) {
                run(Direction.Up);
            }
            else {
                stop();
            }
        }
    }
    
}
