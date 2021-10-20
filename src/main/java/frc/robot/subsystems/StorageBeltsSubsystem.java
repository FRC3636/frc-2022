package frc.robot.subsystems;


import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class StorageBeltsSubsystem extends SubsystemBase {

    private final PWMVictorSPX beltMotor = new PWMVictorSPX(Constants.StorageBelts.BELT_MOTOR);

    public StorageBeltsSubsystem() {

    }

    public void runBelts() {
        beltMotor.set(0.5);
    }
}

