package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
    private final VictorSP motor = new VictorSP(Constants.Intake.MOTOR);

    public IntakeSubsystem() {
        motor.setInverted(true);
    }

    public void setRunning(boolean running) {
        motor.set(running ? 1 : 0);
    }
}
