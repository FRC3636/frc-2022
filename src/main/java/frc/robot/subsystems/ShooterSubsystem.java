package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    PWMVictorSPX leftMotor = new PWMVictorSPX(Constants.LEFT_SHOOTER_MOTOR);
    PWMVictorSPX rightMotor = new PWMVictorSPX(Constants.RIGHT_SHOOTER_MOTOR);

    public ShooterSubsystem() {
        leftMotor.setInverted(true);
    }

    public void spin(double speed) {
        leftMotor.set(speed);
        rightMotor.set(speed);
    }
}
