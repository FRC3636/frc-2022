package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.sql.SQLOutput;

public class ShooterSubsystem extends SubsystemBase {

    private final TalonFX bottomMotor, topMotor;

    public ShooterSubsystem () {
        bottomMotor = new TalonFX(Constants.Shooter.BOTTOM);
        topMotor = new TalonFX(Constants.Shooter.TOP);
        bottomMotor.setInverted(true);

        topMotor.config_kP(0, 0.0195);
        topMotor.config_kI(0, 0);
        topMotor.config_kD(0, 0);
        topMotor.config_kF(0, 0);
        bottomMotor.config_kP(0, 0.0197);
        bottomMotor.config_kI(0, 0);
        bottomMotor.config_kD(0, 0);
        bottomMotor.config_kF(0, 0);

        bottomMotor.selectProfileSlot(0, 0);
        topMotor.selectProfileSlot(0, 0);
//        System.out.println(topMotor.getSelectedSensorVelocity() / 2048);
    }

    public void stop() {
        bottomMotor.set(ControlMode.PercentOutput, 0);
        topMotor.set(ControlMode.PercentOutput, 0);
    }

    public void run(double bottomShooterSpeed, double topShooterSpeed) {

        bottomMotor.set(ControlMode.Velocity, bottomShooterSpeed / Constants.Shooter.VELOCITY_TO_RPM);
        topMotor.set(ControlMode.Velocity, topShooterSpeed / Constants.Shooter.VELOCITY_TO_RPM);
//        System.out.println(bottomShooterSpeed * Constants.Shooter.VELOCITY_TO_RPM + ", " + topShooterSpeed * Constants.Shooter.VELOCITY_TO_RPM);
        System.out.println(bottomMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM + ", " + topMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM);
    }
}
