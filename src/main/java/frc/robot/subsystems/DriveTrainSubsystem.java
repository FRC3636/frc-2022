package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {
    private final Spark motorLeft = new Spark(Constants.Drivetrain.MOTOR_LEFT);
    private final Spark motorRight = new Spark(Constants.Drivetrain.MOTOR_RIGHT);

    private final DifferentialDrive drive = new DifferentialDrive(motorLeft, motorRight);

    public DriveTrainSubsystem() {}

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {

    }

    public void stop() {
        drive.stopMotor();
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        drive.arcadeDrive(xSpeed, zRotation);
    }
}

