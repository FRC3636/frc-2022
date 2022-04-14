/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Shooter extends SubsystemBase {

    private final TalonFX bottomMotor, topMotor;

    private double topSetpoint, bottomSetpoint;


    public Shooter() {
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

        bottomMotor.enableVoltageCompensation(true);
        topMotor.enableVoltageCompensation(true);

        bottomMotor.selectProfileSlot(0, 0);
        topMotor.selectProfileSlot(0, 0);

        RobotContainer.shooterTab.addNumber("Top Shooter Speed", this::getTopShooterSpeed);
        RobotContainer.shooterTab.addNumber("Bottom Shooter Speed", this::getBottomShooterSpeed);

        RobotContainer.shooterTab.addNumber("Top Shooter Setpoint", this::getTopSetpoint);
        RobotContainer.shooterTab.addNumber("Bottom Shooter Setpoint", this::getBottomSetpoint);
    }

    public void stop() {
        bottomMotor.set(ControlMode.PercentOutput, 0);
        topMotor.set(ControlMode.PercentOutput, 0);
    }

    public void run(double bottomShooterSpeed, double topShooterSpeed) {
        topSetpoint = topShooterSpeed;
        bottomSetpoint = bottomShooterSpeed;
        bottomMotor.set(ControlMode.Velocity, bottomShooterSpeed / Constants.Shooter.VELOCITY_TO_RPM);
        topMotor.set(ControlMode.Velocity, topShooterSpeed / Constants.Shooter.VELOCITY_TO_RPM);
    }

    public double getBottomShooterSpeed() {
        return bottomMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM;
    }

    public double getTopShooterSpeed() {
        return topMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM;
    }

    public double getTopSetpoint() {
        return topSetpoint;
    }

    public double getBottomSetpoint() {
        return bottomSetpoint;
    }

    public boolean atSetSpeed() {
        return Math.abs(topSetpoint - topMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM) < 50
                && Math.abs(bottomSetpoint - bottomMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM) < 50;
    }
}
