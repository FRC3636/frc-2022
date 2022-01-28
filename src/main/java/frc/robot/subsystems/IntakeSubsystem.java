package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeMotor;

    public IntakeSubsystem() {
        intakeMotor = new TalonFX(Constants.Intake.MOTOR);
    }

    public void run(Direction direction) {
        intakeMotor.set(TalonFXControlMode.PercentOutput,direction == Direction.In ? 1 : -1);
    }

    public void stop() {
        intakeMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    public enum Direction {
        In,
        Out
    }

}
