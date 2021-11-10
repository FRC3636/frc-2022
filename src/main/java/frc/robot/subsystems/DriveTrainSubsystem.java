/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Drivetrain;
import frc.robot.Constants.Telemetry;
import frc.robot.telemetry.Odometry;

public class DriveTrainSubsystem extends SubsystemBase {

  private final Spark motorLeft = new Spark(Constants.Drivetrain.MOTOR_LEFT);
  private final Spark motorRight = new Spark(Constants.Drivetrain.MOTOR_RIGHT);

  private final Encoder leftEncoder = new Encoder(Drivetrain.LEFT_ENCODER_PORT_1,
      Drivetrain.LEFT_ENCODER_PORT_2, true);
  private final Encoder rightEncoder = new Encoder(Drivetrain.RIGHT_ENCODER_PORT_1,
      Drivetrain.RIGHT_ENCODER_PORT_2, false);

  private final DifferentialDrive drive = new DifferentialDrive(motorLeft, motorRight);

  private final Odometry odometry = new Odometry(leftEncoder, rightEncoder);

  public DriveTrainSubsystem() {
    leftEncoder.setDistancePerPulse(Telemetry.WHEEL_CIRCUMFERENCE / Telemetry.PULSES_PER_ROTATION);
    rightEncoder.setDistancePerPulse(Telemetry.WHEEL_CIRCUMFERENCE / Telemetry.PULSES_PER_ROTATION);
  }

  @Override
  public void periodic() {
    odometry.updateOdometry();
    System.out.println("Heading: " + odometry.getCurrentAngle() + ", Pos: (" + odometry.getCurrentPos().x + ", " + odometry.getCurrentPos().y + ")");
  }

  @Override
  public void simulationPeriodic() {
  }

  public void stop() {
    drive.stopMotor();
  }

  public void arcadeDrive(double xSpeed, double zRotation) {
    drive.arcadeDrive(-xSpeed, zRotation);
  }

  public double getDistance() {
    return rightEncoder.getDistance();
  }

  public double getLeftEncoder() {
    return leftEncoder.getDistance();
  }

  public double getRightEncoder() {
    return rightEncoder.getDistance();
  }

  public void tankDrive(double left, double right) {
    drive.tankDrive(left, right);
  }

  public void zeroEncoders(){
    odometry.zero();
  }

}
