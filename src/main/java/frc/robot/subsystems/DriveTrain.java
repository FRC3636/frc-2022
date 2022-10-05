package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
    private final Spark leftMotor, rightMotor;

    private final DifferentialDrive driveTrain;

    public DriveTrain() {
        leftMotor = new Spark(Constants.Drivetrain.MOTOR_LEFT);
        rightMotor = new Spark(Constants.Drivetrain.MOTOR_RIGHT);

        driveTrain = new DifferentialDrive(leftMotor, rightMotor);

        leftMotor.setInverted(true);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {

    }

    public void stop() {
        leftMotor.set(0);
        rightMotor.set(0);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        driveTrain.arcadeDrive(xSpeed, zRotation);
    }

    public void tankDrive(double speedL, double speedR) {
        driveTrain.tankDrive(speedL, speedR);
    }
}