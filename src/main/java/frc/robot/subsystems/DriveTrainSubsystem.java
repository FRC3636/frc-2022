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
    private final TalonFX leftMotor1, leftMotor2, rightMotor1, rightMotor2;

    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), new Pose2d());
    private final AHRS ahrs = new AHRS();

    public DriveTrainSubsystem() {
        leftMotor1 = new TalonFX(Constants.Drivetrain.MOTOR_LEFT_1);
        leftMotor2 = new TalonFX(Constants.Drivetrain.MOTOR_LEFT_2);
        rightMotor1 = new TalonFX(Constants.Drivetrain.MOTOR_RIGHT_1);
        rightMotor2 = new TalonFX(Constants.Drivetrain.MOTOR_RIGHT_2);
        rightMotor1.setInverted(true);
        rightMotor2.setInverted(true);
        leftMotor1.setInverted(false);
        leftMotor2.setInverted(false);
        resetEncoders();
        resetOdometry(new Pose2d(0, 0, new Rotation2d(0)));

    }

    @Override
    public void periodic() {
       odometry.update(
            Rotation2d.fromDegrees(-ahrs.getRotation2d().getDegrees()),
            leftMotor1.getSelectedSensorPosition() / Constants.Drivetrain.SENSOR_UNITS_PER_METER,
               rightMotor1.getSelectedSensorPosition() / Constants.Drivetrain.SENSOR_UNITS_PER_METER
        );
//        System.out.println(odometry.getPoseMeters());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
                leftMotor1.getSelectedSensorVelocity() / Constants.Drivetrain.SENSOR_UNITS_PER_METER,
                rightMotor1.getSelectedSensorVelocity() / Constants.Drivetrain.SENSOR_UNITS_PER_METER
        );
    }

    public void resetEncoders() {
        leftMotor1.setSelectedSensorPosition(0);
        rightMotor1.setSelectedSensorPosition(0);
        ahrs.reset();
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, ahrs.getRotation2d());
    }

    public void tankDriveVolts(double left, double right) {
        leftMotor1.set(ControlMode.PercentOutput, left / RobotController.getBatteryVoltage());
        rightMotor1.set(ControlMode.PercentOutput, right / RobotController.getBatteryVoltage());
        leftMotor2.set(ControlMode.PercentOutput, left / RobotController.getBatteryVoltage());
        rightMotor2.set(ControlMode.PercentOutput, right / RobotController.getBatteryVoltage());
    }

    public void stop() {
        leftMotor1.set(ControlMode.PercentOutput, 0);
        rightMotor1.set(ControlMode.PercentOutput, 0);
        leftMotor2.set(ControlMode.PercentOutput, 0);
        rightMotor2.set(ControlMode.PercentOutput, 0);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        double turnDiff = Math.copySign(Math.pow(zRotation, 2), zRotation) / ((Math.pow(xSpeed, 2) / 3) + 0.5);
        double leftMotorOutput = Math.copySign(Math.pow(xSpeed, 2), -xSpeed) + turnDiff;
        double rightMotorOutput = Math.copySign(Math.pow(xSpeed, 2), -xSpeed) - turnDiff;

        leftMotor1.set(ControlMode.PercentOutput, leftMotorOutput);
        rightMotor1.set(ControlMode.PercentOutput, rightMotorOutput);
        leftMotor2.set(ControlMode.PercentOutput, leftMotorOutput);
        rightMotor2.set(ControlMode.PercentOutput, rightMotorOutput);
    }

    public void tankDrive(double left, double right) {
        leftMotor1.set(ControlMode.PercentOutput, left);
        leftMotor2.set(ControlMode.PercentOutput, left);
        rightMotor1.set(ControlMode.PercentOutput, right);
        rightMotor2.set(ControlMode.PercentOutput, right);
    }
}

