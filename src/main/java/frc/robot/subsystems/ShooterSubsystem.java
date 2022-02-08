package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    private final TalonFX bottomMotor, topMotor;

    public ShooterSubsystem () {
        bottomMotor = new TalonFX(Constants.Shooter.BOTTOM);
        topMotor = new TalonFX(Constants.Shooter.TOP);
        bottomMotor.setInverted(true);
//        System.out.println(topMotor.getSelectedSensorVelocity() / 2048);
    }

    public void stop() {
        bottomMotor.set(ControlMode.PercentOutput, 0);
        topMotor.set(ControlMode.PercentOutput, 0);
    }

    public void run(double bottomShooterSpeed, double topShooterSpeed) {
        bottomMotor.set(ControlMode.PercentOutput, bottomShooterSpeed);
        topMotor.set(ControlMode.PercentOutput, topShooterSpeed);
        System.out.println(bottomMotor.getSelectedSensorVelocity() / 2048 + ", " + topMotor.getSelectedSensorVelocity() / 2048);
    }
}
