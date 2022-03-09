package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.sql.SQLOutput;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeMotor = new TalonFX(Constants.Intake.MOTOR);
    private final CANSparkMax winchMotor = new CANSparkMax(Constants.Intake.WINCH_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    private final DigitalInput winchLimitSwitch = new DigitalInput(Constants.Intake.WINCH_LIMIT_SWITCH);


    public IntakeSubsystem() {
//        winchMotor.setSmartCurrentLimit(20);
        winchMotor.getEncoder().setPosition(0);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
    }

    public void run(Direction direction) {
        intakeMotor.set(TalonFXControlMode.PercentOutput, direction == Direction.In ? 1 : -1);
    }

    public void winch(WinchDirection winchDirection) {
        if (!winchLimitSwitch.get() && winchDirection.equals(WinchDirection.Up)) {
            winchMotor.getEncoder().setPosition(0);
            stopWinch();
        }
        else if (
                (winchMotor.getEncoder().getPosition() * 1.8) / Constants.Intake.WINCH_MOTOR_GEAR_RATIO > Constants.Intake.WINCH_MAX_REVOLUTIONS &&
                        winchDirection.equals(WinchDirection.Down)
        ){
            stopWinch();
        }
        else {
            winchMotor.set(winchDirection.equals(WinchDirection.Down) ? 0.5 : -0.5);
        }
    }

    public void stopWinch() {
        winchMotor.set(0);
        System.out.println("stop");
    }

    public void stop() {
        intakeMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    public enum WinchDirection {
        Up,
        Down
    }

    public enum Direction {
        In,
        Out
    }

}
