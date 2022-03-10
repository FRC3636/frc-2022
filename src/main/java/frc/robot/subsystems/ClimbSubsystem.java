package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {

    private final TalonFX rightTelescopingMotor = new TalonFX(Constants.Climb.RIGHT_TELESCOPING_MOTOR);
    private final TalonFX leftTelescopingMotor = new TalonFX(Constants.Climb.LEFT_TELESCOPING_MOTOR);

    private final CANSparkMax rightPivotMotor = new CANSparkMax(Constants.Climb.RIGHT_PIVOT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax leftPivotMotor = new CANSparkMax(Constants.Climb.LEFT_PIVOT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DigitalInput pivotLimitSwitchOut = new DigitalInput(Constants.Climb.PIVOT_LIMIT_SWITCH_OUT);
    private final DigitalInput pivotLimitSwitchIn = new DigitalInput(Constants.Climb.PIVOT_LIMIT_SWITCH_IN);

    public ClimbSubsystem() {
        rightTelescopingMotor.setNeutralMode(NeutralMode.Brake);
        leftTelescopingMotor.setNeutralMode(NeutralMode.Brake);


    }

    public double EPSILON = 0.1;
    public void runClimb(double telescopeSpeed, double pivotSpeed) {
        rightTelescopingMotor.set(TalonFXControlMode.PercentOutput, Math.abs(telescopeSpeed) < EPSILON ? 0 : telescopeSpeed);
        leftTelescopingMotor.set(TalonFXControlMode.PercentOutput, Math.abs(telescopeSpeed) < EPSILON ? 0 : telescopeSpeed);

        if ((pivotSpeed > 0 && !pivotLimitSwitchOut.get()) || (pivotSpeed < 0 && !pivotLimitSwitchIn.get())) {
            rightPivotMotor.set(0);
            leftPivotMotor.set(0);
        } else {
            rightPivotMotor.set(Math.abs(pivotSpeed) < EPSILON ? 0 : pivotSpeed);
            leftPivotMotor.follow(rightPivotMotor, true);
        }
    }

    public void stop() {
        rightTelescopingMotor.set(TalonFXControlMode.PercentOutput, 0);
        leftTelescopingMotor.set(TalonFXControlMode.PercentOutput, 0);

        rightPivotMotor.set(0);
        leftPivotMotor.set(0);
    }
}
