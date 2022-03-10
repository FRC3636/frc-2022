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
        pidController = new PIDController(0.0079, 0.00001, 0.0003);
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
//        double fakeAngle = (10 / (1 + Math.pow(1.6, -camera.getAngleToGoalDegrees()))) - 5;

        double fakeAngle = Math.copySign(Math.abs(camera.getAngleToGoalDegrees()) < 10 ? camera.getAngleToGoalDegrees() : 10
                , camera.getAngleToGoalDegrees());

        System.out.println("Angle: " + camera.getAngleToGoalDegrees() + ", Fake Angle: " + fakeAngle);

        double turn = pidController.calculate(fakeAngle, 0);

        System.out.println("Output: " + turn);

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
