package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {
  private final Spark climb = new Spark(Constants.climb.CLIMB_MOTOR);
  
  public ClimbSubsystem() {}

  @Override
  public void periodic() {
  }

  public void extend(double magnitude){
     climb.set(magnitude);
  }

  public void stop(){
    climb.set(0);
  }

}
