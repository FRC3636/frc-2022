/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private final TalonFX intakeMotor = new TalonFX(Constants.Intake.MOTOR);

    private boolean intakeLocked;

    private final CANSparkMax actuationMotor =
            new CANSparkMax(
                    Constants.Intake.ACTUATION_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    private final DigitalInput limitSwitch1 =
            new DigitalInput(Constants.Intake.ACTUATION_LIMIT_SWITCH_1);
    private final DigitalInput limitSwitch2 =
            new DigitalInput(Constants.Intake.ACTUATION_LIMIT_SWITCH_2);

    private Position position = Position.Up;

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
    }

    public void run(double direction) {
        intakeMotor.set(TalonFXControlMode.PercentOutput, direction * 0.5);
    }

    @Override
    public void periodic() {
        if (position == Position.Up) {
            if (!limitSwitch1.get() || !limitSwitch2.get()) {
                actuationMotor.getEncoder().setPosition(0);
                actuationMotor.set(0);
                position = Position.HoldUp;
                actuationMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
            } else {
                actuationMotor.set(0.27);
            }
        } else if (position == Position.Down
                && Math.abs(
                                actuationMotor.getEncoder().getPosition()
                                        / Constants.Intake.ACTUATION_MOTOR_GEAR_RATIO
                                        * 360)
                        < Constants.Intake.ACTUATION_DEGREES) {
            actuationMotor.set(-0.2);
        }
        else if (position == Position.HoldUp) {
            if (!limitSwitch1.get() || !limitSwitch2.get()) {
                actuationMotor.getEncoder().setPosition(0);
                actuationMotor.set(0);
            } else {
                System.out.println("false");
                actuationMotor.set(0.1);
            }
        }
        else {
            position = Position.Coast;
            actuationMotor.set(0);
        }
    }

    public void setIntakeUp() {
        if(!intakeLocked) {
            position = Position.Up;
            actuationMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }
    }

    public void setIntakeDown() {
        if(!intakeLocked) {
            actuationMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
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
        Coast,
        HoldUp
    }
}
