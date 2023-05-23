/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTrain extends SubsystemBase {
    private final WPI_TalonFX leftMotor1, leftMotor2, rightMotor1, rightMotor2;

    private final DifferentialDriveOdometry odometry =
            new DifferentialDriveOdometry(new Rotation2d(), 0, 0);
    private final AHRS navX = new AHRS();

    public DriveTrain() {
        leftMotor1 = new WPI_TalonFX(Constants.Drivetrain.MOTOR_LEFT_1);
        leftMotor2 = new WPI_TalonFX(Constants.Drivetrain.MOTOR_LEFT_2);
        rightMotor1 = new WPI_TalonFX(Constants.Drivetrain.MOTOR_RIGHT_1);
        rightMotor2 = new WPI_TalonFX(Constants.Drivetrain.MOTOR_RIGHT_2);

        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);

        rightMotor1.setInverted(true);
        rightMotor2.setInverted(true);
        leftMotor1.setInverted(false);
        leftMotor2.setInverted(false);
//        rightMotor1.enableVoltageCompensation(true);
//        rightMotor2.enableVoltageCompensation(true);
//        leftMotor1.enableVoltageCompensation(true);
//        leftMotor2.enableVoltageCompensation(true);

        setNeutralMode(NeutralMode.Coast);


        resetEncoders();
        resetOdometry(new Pose2d(0, 0, new Rotation2d(0)));

        RobotContainer.cameraTab.addNumber("Gyro", this::getRotation);
    }

    @Override
    public void periodic() {
        odometry.update(
                Rotation2d.fromDegrees(-navX.getRotation2d().getDegrees()),
                leftMotor1.getSelectedSensorPosition()
                        / Constants.Drivetrain.SENSOR_UNITS_PER_METER,
                rightMotor1.getSelectedSensorPosition()
                        / Constants.Drivetrain.SENSOR_UNITS_PER_METER);

        RobotContainer.field.setRobotPose(odometry.getPoseMeters());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
                leftMotor1.getSelectedSensorVelocity()
                        / Constants.Drivetrain.SENSOR_UNITS_PER_METER,
                rightMotor1.getSelectedSensorVelocity()
                        / Constants.Drivetrain.SENSOR_UNITS_PER_METER);
    }

    public void resetEncoders() {
        leftMotor1.setSelectedSensorPosition(0);
        rightMotor1.setSelectedSensorPosition(0);
        navX.reset();
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(navX.getRotation2d(), leftMotor1.getSelectedSensorPosition(), rightMotor1.getSelectedSensorPosition(), pose);
    }

    public void tankDriveVolts(double left, double right) {
        leftMotor1.setVoltage(left);
        rightMotor1.setVoltage(right);
    }

    public void stop() {
        leftMotor1.set(ControlMode.PercentOutput, 0);
        rightMotor1.set(ControlMode.PercentOutput, 0);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        double turnDiff =
                Math.copySign(Math.pow(zRotation, 2), zRotation)
                        / ((Math.pow(xSpeed, 2) / 3) + 0.5);
        double leftMotorOutput = Math.copySign(Math.pow(xSpeed, 2), -xSpeed) + turnDiff;
        double rightMotorOutput = Math.copySign(Math.pow(xSpeed, 2), -xSpeed) - turnDiff;

        leftMotor1.set(leftMotorOutput);
        rightMotor1.set(rightMotorOutput);
    }

    public void tankDrive(double left, double right) {
        leftMotor1.set(ControlMode.PercentOutput, left);
        rightMotor1.set(ControlMode.PercentOutput, right);
    }

    public void setNeutralMode(NeutralMode mode) {
        leftMotor1.setNeutralMode(mode);
        leftMotor2.setNeutralMode(mode);
        rightMotor1.setNeutralMode(mode);
        rightMotor2.setNeutralMode(mode);
    }

    public double getRotation() {
        return -navX.getRotation2d().getDegrees();
    }

}
