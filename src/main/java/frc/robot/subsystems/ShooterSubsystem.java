/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShooterSubsystem extends SubsystemBase {

    private final TalonFX bottomMotor, topMotor;

//    private final PIDController topOutput, bottomOutput;


    public ShooterSubsystem() {
        bottomMotor = new TalonFX(Constants.Shooter.BOTTOM);
        topMotor = new TalonFX(Constants.Shooter.TOP);
        bottomMotor.setInverted(true);

//        topOutput = new PIDController(0, 0, 0);
//        bottomOutput = new PIDController(0, 0, 0);
//
//        RobotContainer.shooterTab.add("Bottom Shooter PID", topOutput).withWidget(BuiltInWidgets.kPIDController);
//        RobotContainer.shooterTab.add("Top Shooter PID", bottomOutput).withWidget(BuiltInWidgets.kPIDController);

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

        RobotContainer.shooterTab.addNumber("Top Shooter Speed", this::getTopShooterSpeed);
        RobotContainer.shooterTab.addNumber("Bottom Shooter Speed", this::getBottomShooterSpeed);
    }

    public void stop() {
        bottomMotor.set(ControlMode.PercentOutput, 0);
        topMotor.set(ControlMode.PercentOutput, 0);
    }

    public void run(double bottomShooterSpeed, double topShooterSpeed) {
        bottomMotor.set(ControlMode.Velocity, bottomShooterSpeed / Constants.Shooter.VELOCITY_TO_RPM);
        topMotor.set(ControlMode.Velocity, topShooterSpeed / Constants.Shooter.VELOCITY_TO_RPM);

//        double topSpeed = topOutput.calculate(topMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM, topShooterSpeed);
//        double bottomSpeed = bottomOutput.calculate(bottomMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM, bottomShooterSpeed);
//
//        topMotor.set(ControlMode.PercentOutput, topSpeed / RobotController.getBatteryVoltage());
//        bottomMotor.set(ControlMode.PercentOutput, bottomSpeed / RobotController.getBatteryVoltage());
    }

    public double getBottomShooterSpeed() {
        return bottomMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM;
    }

    public double getTopShooterSpeed() {
        return topMotor.getSelectedSensorVelocity() * Constants.Shooter.VELOCITY_TO_RPM;
    }
}
