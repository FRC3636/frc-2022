package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {
    private final CANSparkMax conveyorMotor;
    DigitalInput input = new DigitalInput(0);
    public ConveyorSubsystem() {
        conveyorMotor = new CANSparkMax(Constants.Conveyor.MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        conveyorMotor.setSmartCurrentLimit(20);
    }

    public void run(Direction direction) {
        conveyorMotor.set(direction == Direction.Up ? 1 : -1);
    }
    public boolean checkBeam (){
        return(input.get());
    }

    public void stop() {
        conveyorMotor.set(0);
    }

    public enum Direction {
        Up,
        Down
    }

    @Override
    public void periodic(){
        System.out.println(input.get());
    }
    
}
