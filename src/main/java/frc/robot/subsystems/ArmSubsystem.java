import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private final Spark motor = new Spark(Constants.Arm.MOTOR);
  private final Encoder encoder = new Encoder(Constants.Arm.Encoder);

  private final PIDController pid = new PIDController(Constants.Arm.P, Constants.Arm.I, Constants.Arm.D);

  private State state = State.InPerimeter;

  public ArmSubsystem() {}

  @Override
  public void periodic() {
    motor.set(pid.calculat(encoder.getDistance(), state));
  }

  public void setTarget(State state) {
    this.state = state;
  }

  public enum State {
    Intake = 0.0,
    Outtake = 0.5,
    InPerimeter = 1,
  }
}
