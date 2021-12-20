/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Drivetrain;

public class DriveTrainSubsystem extends SubsystemBase {

  private final Spark motorLeft = new Spark(Constants.Drivetrain.MOTOR_LEFT);
  private final Spark motorRight = new Spark(Constants.Drivetrain.MOTOR_RIGHT);

  private final Encoder encoderLeft =
      new Encoder(Drivetrain.LEFT_ENCODER_PORT_1, Drivetrain.LEFT_ENCODER_PORT_2, true);
  private final Encoder encoderRight =
      new Encoder(Drivetrain.RIGHT_ENCODER_PORT_1, Drivetrain.RIGHT_ENCODER_PORT_2, false);

  private final DifferentialDrive drive = new DifferentialDrive(motorLeft, motorRight);

  private final Gyro gyro = new ADXRS450_Gyro();
  private final DifferentialDriveOdometry odometry =
      new DifferentialDriveOdometry(gyro.getRotation2d());

  public DriveTrainSubsystem() {
    encoderLeft.setDistancePerPulse(Drivetrain.WHEEL_CIRCUMFERENCE / Drivetrain.PULSES_PER_ROTATION);
    encoderRight.setDistancePerPulse(Drivetrain.WHEEL_CIRCUMFERENCE / Drivetrain.PULSES_PER_ROTATION);
    resetEncoders();
  }

  @Override
  public void periodic() {
    odometry.update(gyro.getRotation2d(), encoderLeft.getDistance(), encoderRight.getDistance());
    Pose2d pose = odometry.getPoseMeters();
//    System.out.println(
//        "Heading: " + pose.getRotation() + ", Pos: (" + pose.getX() + ", " + pose.getY() + ")");
  }

  public void stop() {
    drive.stopMotor();
  }

  public void arcadeDrive(double xSpeed, double zRotation) {
    drive.arcadeDrive(-xSpeed, zRotation);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    motorLeft.setVoltage(leftVolts);
    motorRight.setVoltage(-rightVolts);
    drive.feed();
  }

  public void tankDrive(double left, double right) {
    drive.tankDrive(Math.copySign(left * left, left), Math.copySign(right * right * right, right));
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(encoderLeft.getRate(), encoderRight.getRate());
  }

  public void resetEncoders() {
    encoderLeft.reset();
    encoderRight.reset();
  }
}
