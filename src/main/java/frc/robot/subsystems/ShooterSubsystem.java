package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    private final TalonFX bottomMotor, topMotor;

    public ShooterSubsystem () {
        bottomMotor = new TalonFX(Constants.Shooter.BOTTOM);
        topMotor = new TalonFX(Constants.Shooter.TOP);
        bottomMotor.setInverted(true);

        bottomMotor.config_kP(0, Constants.Shooter.BOTTOM_P);
        bottomMotor.config_kI(0, Constants.Shooter.BOTTOM_I);
        bottomMotor.config_kD(0, Constants.Shooter.BOTTOM_D);
        bottomMotor.config_kF(0, Constants.Shooter.BOTTOM_F);
        topMotor.config_kP(0, Constants.Shooter.TOP_P);
        topMotor.config_kI(0, Constants.Shooter.TOP_I);
        topMotor.config_kD(0, Constants.Shooter.TOP_D);
        topMotor.config_kF(0, Constants.Shooter.TOP_F);


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
//        bottomMotor.set(TalonFXControlMode.PercentOutput, 0.5);
//        topMotor.set(TalonFXControlMode.PercentOutput, 0.5);
        System.out.println("Bottom: " + bottomMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM + ", Top: " + topMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM);
//        System.out.println(bottomMotor.getSelectedSensorVelocity()+ ", " + topMotor.getSelectedSensorVelocity());
//        System.out.println(bottomMotor.getMotorOutputVoltage() + ", " + topMotor.getMotorOutputVoltage());
    }

    public int[] getVelocity() {
        return new int[]{
                (int) (bottomMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM),
                (int) (topMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM)
        };
    }
}
