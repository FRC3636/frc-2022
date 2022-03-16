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

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
    }

    public void run(Direction direction) {
        intakeMotor.set(TalonFXControlMode.PercentOutput, direction == Direction.In ? 1 : -1);
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
