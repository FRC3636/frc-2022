package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTrainSubsystem extends SubsystemBase {
    private final TalonFX leftMotor, rightMotor;
    private boolean enabled = false;

    public DriveTrainSubsystem() {
        leftMotor = new TalonFX(Constants.Drivetrain.MOTOR_LEFT);
        rightMotor = new TalonFX(Constants.Drivetrain.MOTOR_RIGHT);

        leftMotor.setInverted(true);
    }

    @Override
    public void periodic() {
        if (!enabled) {
            stop();
        }
    }

    @Override
    public void simulationPeriodic() {

    }

    public void stop() {
        leftMotor.set(ControlMode.PercentOutput, 0);
        rightMotor.set(ControlMode.PercentOutput, 0);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        if (!enabled) return;

        double turnDiff = Math.copySign(Math.pow(zRotation, 2), zRotation) / ((Math.pow(xSpeed, 2) / 3) + 0.5);
        double leftMotorOutput = Math.copySign(Math.pow(xSpeed, 2), -xSpeed) - turnDiff;
        double rightMotorOutput = Math.copySign(Math.pow(xSpeed, 2), -xSpeed) + turnDiff;

        leftMotor.set(ControlMode.PercentOutput, leftMotorOutput);
        rightMotor.set(ControlMode.PercentOutput, rightMotorOutput);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

