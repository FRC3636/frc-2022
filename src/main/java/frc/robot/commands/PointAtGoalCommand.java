package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class PointAtGoalCommand extends CommandBase {

    private final CameraSubsystem camera;
    private final DriveTrainSubsystem driveTrain;

    private final PIDController pidController;

    public PointAtGoalCommand(DriveTrainSubsystem driveTrain, CameraSubsystem camera) {
        this.driveTrain = driveTrain;
        this.camera = camera;
        pidController = new PIDController(0.009, 0, 0);
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        double fakeAngle = (20 / (1 + Math.pow(1.7, -camera.getAngleToGoalDegrees()))) - 10;

        System.out.println("Angle: " + camera.getAngleToGoalDegrees() + ", Fake Angle: " + fakeAngle);

        double turn = pidController.calculate(fakeAngle, 0);

        driveTrain.tankDrive(-turn, turn);
    }

    @Override
    public boolean isFinished() {
        return(camera.getAngleToGoalDegrees() < 1 &&
                Math.abs(driveTrain.getWheelSpeeds().leftMetersPerSecond) < 0.001 &&
                Math.abs(driveTrain.getWheelSpeeds().rightMetersPerSecond) < 0.001
        );
    }
}
