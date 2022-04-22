/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import java.util.ArrayList;

public class Conveyor extends SubsystemBase {
    private final CANSparkMax conveyorMotor;
    private final DigitalInput beamBreak = new DigitalInput(Constants.Conveyor.BEAM_BREAK);
    private final ArrayList<Double> conveyorCurrentHistory = new ArrayList<>();

    public Conveyor() {
        conveyorMotor = new CANSparkMax(Constants.Conveyor.MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        conveyorMotor.setSmartCurrentLimit(20);

        RobotContainer.driveSettings.addNumber("Conveyor Current", () -> getCurrentRollingAvg(Constants.Conveyor.CURRENT_SMA_PERIOD));
    }

    public void run(Direction direction) {
        conveyorMotor.set(direction == Direction.Up ? 0.75 : -0.75);
    }
    public void stop() {
        conveyorMotor.set(0);
    }

    @Override
    public void periodic() {
        conveyorCurrentHistory.add(getCurrent());


    }

    public enum Direction {
        Up,
        Down
    }

    public boolean getBeamBreak() {
        return beamBreak.get();
    }

    public double getCurrent() {
        return conveyorMotor.getOutputCurrent();
    }

    public double getCurrentRollingAvg(int period) {
        if (period > conveyorCurrentHistory.size()) {
            return 0;
        }

        return conveyorCurrentHistory.subList(conveyorCurrentHistory.size() - period, conveyorCurrentHistory.size()).stream().reduce(0.0, Double::sum) / period;
    }
}
