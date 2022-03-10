/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeMotor = new TalonFX(Constants.Intake.MOTOR);
    private final CANSparkMax winchMotor =
            new CANSparkMax(Constants.Intake.WINCH_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    private final DigitalInput winchLimitSwitch =
            new DigitalInput(Constants.Intake.WINCH_LIMIT_SWITCH);

    private final PowerDistribution powerDistribution = new PowerDistribution();

    private Position winchPosition = Position.Up;

    public IntakeSubsystem() {
        winchMotor.setSmartCurrentLimit(20);
        winchMotor.getEncoder().setPosition(0);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
    }

    public void run(Direction direction) {
        intakeMotor.set(TalonFXControlMode.PercentOutput, direction == Direction.In ? 1 : -1);
    }

    public void winch(Position winchDirection) {
        if (!winchLimitSwitch.get() && winchDirection.equals(Position.Up)) {
            winchMotor.getEncoder().setPosition(0);
            stopWinch();
        } else if ((winchMotor.getEncoder().getPosition() * 1.8)
                                / Constants.Intake.WINCH_MOTOR_GEAR_RATIO
                        > Constants.Intake.WINCH_MAX_REVOLUTIONS
                && winchDirection.equals(Position.Down)) {
            stopWinch();
        } else if (false) {
            stopWinch();
        } else {
            winchMotor.set(winchDirection.equals(Position.Down) ? -0.5 : 0.5);
        }
    }

    @Override
    public void periodic() {
        //        winch(winchPosition);
    }

    public void winchUp() {
        winchPosition = Position.Up;
    }

    public void winchDown() {
        winchPosition = Position.Down;
    }

    public void stopWinch() {
        winchMotor.set(0);
        System.out.println("stop");
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
