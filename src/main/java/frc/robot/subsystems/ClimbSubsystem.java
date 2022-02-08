package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {

    private final TalonFX motor = new TalonFX(Constants.Climb.MOTOR);

    public ClimbSubsystem() {

    }

    public void runMotor(double speed) {
        motor.set(TalonFXControlMode.PercentOutput, speed);

    }

    public void stop() {
        motor.set(TalonFXControlMode.PercentOutput, 0);
    }
}
