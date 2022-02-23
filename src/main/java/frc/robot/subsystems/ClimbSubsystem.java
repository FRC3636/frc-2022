package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {

    private final TalonFX telescopingMotor = new TalonFX(Constants.Climb.TELESCOPING_MOTOR);

    private final CANSparkMax pivotMotor = new CANSparkMax(Constants.Climb.PIVOTING_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    public ClimbSubsystem() {
        telescopingMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 10, 1, 1));
        pivotMotor.setSmartCurrentLimit(10);
    }

    public void runClimb(double telescopeSpeed, double pivotSpeed) {
        telescopingMotor.set(TalonFXControlMode.PercentOutput, telescopeSpeed);
        pivotMotor.set(pivotSpeed);
    }

    public void stop() {
        telescopingMotor.set(TalonFXControlMode.PercentOutput, 0);
    }
}
