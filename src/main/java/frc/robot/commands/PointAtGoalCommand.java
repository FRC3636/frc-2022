/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class PointAtGoalCommand extends CommandBase {

    private final CameraSubsystem camera;
    private final DriveTrainSubsystem driveTrain;

    private final PIDController pidController = new PIDController(0.06, 0.01, 0.004);

    private Timer timer;

    private double estimatedAngle = 0;

    private final int velocitySize = (int) (50 * Constants.Camera.LATENCY);

    private double[] lastAngles = new double[velocitySize];

    public PointAtGoalCommand(DriveTrainSubsystem driveTrain, CameraSubsystem camera) {
        this.driveTrain = driveTrain;
        this.camera = camera;

        addRequirements(driveTrain);

        try {
            RobotContainer.cameraTab.add("Auto Aim PID",
                    pidController).withWidget(BuiltInWidgets.kPIDController);
            RobotContainer.cameraTab.addNumber("Estimated Angle", this::getEstimatedAngle);
        } catch (Exception ignored) {}
    }

    @Override
    public void initialize() {
        camera.turnOnLight();
        timer = new Timer();
        timer.start();
        lastAngles = new double[velocitySize];

        pidController.setSetpoint(0);
        pidController.setTolerance(2);
    }

    private static final double MAX_OUTPUT = 0.2;

    @Override
    public void execute() {
        int currentIndex = (int)(Timer.getMatchTime() / 50) % velocitySize;
        lastAngles[currentIndex] = driveTrain.getPose().getRotation().getDegrees();

        if(lastAngles[currentIndex + 1] != 0) {
            estimatedAngle = lastAngles[currentIndex + 1] - lastAngles[currentIndex] + camera.getAngleToGoalDegrees();
        }
        else {
            estimatedAngle = camera.getAngleToGoalDegrees();
        }

        double turn = pidController.calculate(getEstimatedAngle(), 0);
        turn = Math.copySign(Math.min(MAX_OUTPUT, Math.abs(turn)), turn);
        driveTrain.tankDrive(-turn, turn);
    }

    public double getEstimatedAngle() {
        return estimatedAngle;
    }

    @Override
    public boolean isFinished() {
        if (!camera.hasResult()) return false;
        return pidController.atSetpoint();
    }
}
