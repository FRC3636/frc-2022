package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {

    private final Spark motorLeft = new Spark(Constants.MOTOR_LEFT);
    private final Spark motorRight = new Spark(Constants.MOTOR_RIGHT);

    public DriveTrainSubsystem() {}

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {

    }

    public void runMotors(double leftMotorSpeed, double rightMotorSpeed) {
        motorLeft.setSpeed(-leftMotorSpeed);
        motorRight.setSpeed(rightMotorSpeed);
    }
}

