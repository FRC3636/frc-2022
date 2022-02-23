package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drivetrain.*;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {
    private final TalonFX leftMotor, rightMotor;

    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), new Pose2d());
    private final AHRS ahrs = new AHRS();

    public DriveTrainSubsystem() {
        leftMotor = new TalonFX(Constants.Drivetrain.MOTOR_LEFT);
        rightMotor = new TalonFX(Constants.Drivetrain.MOTOR_RIGHT);
        leftMotor.setInverted(true);
        resetEncoders();
    }

    @Override
    public void periodic() {
       odometry.update(
            ahrs.getRotation2d(),
            leftMotor.getSelectedSensorPosition() * Constants.Drivetrain.SENSOR_UNITS_PER_METER,
            rightMotor.getSelectedSensorPosition() * Constants.Drivetrain.SENSOR_UNITS_PER_METER
        );
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
            leftMotor.getSelectedSensorVelocity() * Constants.Drivetrain.SENSOR_UNITS_PER_METER,
            rightMotor.getSelectedSensorVelocity() * Constants.Drivetrain.SENSOR_UNITS_PER_METER
        );
    }

    public void resetEncoders() {
        leftMotor.setSelectedSensorPosition(0);
        rightMotor.setSelectedSensorPosition(0);
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, ahrs.getRotation2d());
    }

    public void tankDriveVolts(double left, double right) {
        leftMotor.set(ControlMode.PercentOutput, left / RobotController.getBatteryVoltage());
        rightMotor.set(ControlMode.PercentOutput, right / RobotController.getBatteryVoltage());
    }

    public void stop() {
        leftMotor.set(ControlMode.PercentOutput, 0);
        rightMotor.set(ControlMode.PercentOutput, 0);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        double turnDiff = Math.copySign(Math.pow(zRotation, 2), zRotation) / ((Math.pow(xSpeed, 2) / 3) + 0.5);
        double leftMotorOutput = Math.copySign(Math.pow(xSpeed, 2), -xSpeed) - turnDiff;
        double rightMotorOutput = Math.copySign(Math.pow(xSpeed, 2), -xSpeed) + turnDiff;

        leftMotor.set(ControlMode.PercentOutput, leftMotorOutput);
        rightMotor.set(ControlMode.PercentOutput, rightMotorOutput);
    }
}

