package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeMotor;
    private final CANSparkMax winchMotor;


    public IntakeSubsystem() {
        intakeMotor = new TalonFX(Constants.Intake.MOTOR);
        winchMotor = new CANSparkMax(Constants.Intake.WINCH_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void run(Direction direction) {
        intakeMotor.set(TalonFXControlMode.PercentOutput, direction == Direction.In ? 1 : -1);
    }

    public void winch(Position position) {
        winchMotor.set(position == Position.Down ? 1 : -1);
    }

    public void stopWinch() {
        winchMotor.set(0);
    }

    public void stop() {
        intakeMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    public enum Position {
        Up,
        Down
    }

    public enum Direction {
        In,
        Out
    }

}
