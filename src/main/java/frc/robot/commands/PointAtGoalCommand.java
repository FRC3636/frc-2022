/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class PointAtGoalCommand extends CommandBase {

    private final CameraSubsystem camera;
    private final DriveTrainSubsystem driveTrain;

    private final PIDController pidController;

    private Timer timer;

    private double startingAngle = Float.NaN;
    private double startingGyroRotation = Float.NaN;

    public PointAtGoalCommand(DriveTrainSubsystem driveTrain, CameraSubsystem camera) {
        this.driveTrain = driveTrain;
        this.camera = camera;

        pidController = new PIDController(0.0085, 0, 0.0004);
        RobotContainer.cameraTab.add("Auto Aim PID", pidController).withWidget(BuiltInWidgets.kPIDController);

        addRequirements(driveTrain, camera);

        RobotContainer.cameraTab.addNumber("Estimated Angle", this::getEstimatedAngle);
    }

    @Override
    public void initialize() {
        camera.turnOnLight();
        timer = new Timer();
        timer.start();
        startingAngle = Float.NaN;
        startingGyroRotation = Float.NaN;
    }

    private static final double MAX_OUTPUT = 0.2;

    @Override
    public void execute() {
        if (Double.isNaN(startingAngle) && camera.hasResult()) {
            startingAngle = camera.getAngleToGoalDegrees();
            startingGyroRotation = driveTrain.getPose().getRotation().getDegrees();
        }

        if (!Double.isNaN(startingAngle)) {
//            if(Math.abs(getEstimatedAngle() - camera.getAngleToGoalDegrees()) > 3) {
//                startingAngle = camera.getAngleToGoalDegrees();
//                startingGyroRotation = driveTrain.getPose().getRotation().getDegrees();
//            }
            double turn = pidController.calculate(getEstimatedAngle(), 0) / 3;
            turn = Math.copySign(Math.min(MAX_OUTPUT, Math.abs(turn)), turn);
            driveTrain.tankDrive(-turn, turn);
        }
    }

    public double getEstimatedAngle() {
        return startingAngle + (driveTrain.getPose().getRotation().getDegrees() - startingGyroRotation);
    }

    @Override
    public boolean isFinished() {
        if (!camera.hasResult()) return false;
//         if (timer.hasElapsed(4)) {
//             return true;
//         }

        return Math.abs(camera.getAngleToGoalDegrees()) < 3
                && Math.abs(driveTrain.getWheelSpeeds().leftMetersPerSecond) < 0.001
                && Math.abs(driveTrain.getWheelSpeeds().rightMetersPerSecond) < 0.001;
    }
}
