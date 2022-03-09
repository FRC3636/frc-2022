package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class PointAtGoalCommand extends CommandBase {

    private final CameraSubsystem camera;
    private final DriveTrainSubsystem driveTrain;

    private PIDController pidController;

    public PointAtGoalCommand(DriveTrainSubsystem driveTrain, CameraSubsystem camera) {
        this.driveTrain = driveTrain;
        this.camera = camera;
        pidController = new PIDController(0, 0, 0);
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        turn = pidController.calculate(camera.getAngleToGoalDegrees(), 0)
    }

    @Override
    public boolean isFinished() {
        return(camera.getAngleToGoalDegrees() < 3 &&
                Math.abs(driveTrain.getWheelSpeeds().leftMetersPerSecond) < 0.001 &&
                Math.abs(driveTrain.getWheelSpeeds().rightMetersPerSecond) < 0.001
        );
    }
}
