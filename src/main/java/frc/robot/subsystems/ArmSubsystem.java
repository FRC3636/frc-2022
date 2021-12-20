package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private final Spark motor = new Spark(Constants.Arm.MOTOR);
  private double speed = 0;

  public ArmSubsystem() {
  }

  @Override
  public void periodic() {
    System.out.println("speed: " + speed);
    motor.set(speed);
  }

  public void set(double speed) {
    this.speed = speed;
  }
}
