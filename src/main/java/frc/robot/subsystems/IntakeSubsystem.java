/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeMotor = new TalonFX(Constants.Intake.MOTOR);

    private boolean intakeLocked;

    private final CANSparkMax actuationMotor =
            new CANSparkMax(
                    Constants.Intake.ACTUATION_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    private final DigitalInput limitSwitch =
            new DigitalInput(Constants.Intake.ACTUATION_LIMIT_SWITCH);

    private Position position = Position.Up;

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
    }

    public void run(Direction direction) {
        intakeMotor.set(TalonFXControlMode.PercentOutput, direction == Direction.In ? 1 : -1);
    }

    @Override
    public void periodic() {
        if (position == Position.Up) {
            if (!limitSwitch.get()) {
                actuationMotor.getEncoder().setPosition(0);
                actuationMotor.set(0);
                position = Position.Done;
            } else {
                actuationMotor.set(0.25);
            }
        } else if (position == Position.Down
                && Math.abs(
                                actuationMotor.getEncoder().getPosition()
                                        / Constants.Intake.ACTUATION_MOTOR_GEAR_RATIO
                                        * 360)
                        < Constants.Intake.ACTUATION_DEGREES) {
            actuationMotor.set(-0.2);
        }
        else {
            position = Position.Done;
            actuationMotor.set(0);
        }
    }

    public void setIntakeUp() {
        if(!intakeLocked) {
            position = Position.Up;
            intakeMotor.setNeutralMode(NeutralMode.Brake);
        }
    }

    public void setIntakeDown() {
        if(!intakeLocked) {
            intakeMotor.setNeutralMode(NeutralMode.Coast);
            position = Position.Down;
        }
    }

    public void setIntakeLocked(boolean intakeLocked) {
        this.intakeLocked = intakeLocked;
    }

    public void stop() {
        intakeMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    public enum Position {
        Up,
        Down,
        Done
    }

    public enum Direction {
        In,
        Out
    }
}
