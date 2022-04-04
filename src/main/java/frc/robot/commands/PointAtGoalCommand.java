/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class PointAtGoalCommand extends CommandBase {

    private final CameraSubsystem camera;
    private final DriveTrainSubsystem driveTrain;

    private final PIDController pidController = new PIDController(Autonomous.TURN_KP, Autonomous.TURN_KP, Autonomous.TURN_KP);

    private Timer timer;

    private final int velocitySize = (int) (50 * Camera.LATENCY);

    private static NetworkTableEntry setTurn = RobotContainer.cameraTab.add("Turn Distance", 0).withWidget(BuiltInWidgets.kTextView).getEntry();;

    private double startingAngle = Float.NaN;
    private double startingGyroRotation = Float.NaN;

    public PointAtGoalCommand(DriveTrainSubsystem driveTrain, CameraSubsystem camera) {
        this.driveTrain = driveTrain;
        this.camera = camera;

        addRequirements(driveTrain);

        pidController.setTolerance(2);

        try {
            RobotContainer.cameraTab.add("Auto Aim PID",
                    pidController).withWidget(BuiltInWidgets.kPIDController);
            RobotContainer.cameraTab.addNumber("Estimated Angle", this::getEstimatedAngle);
            RobotContainer.cameraTab.addNumber("Angle Offset", this::getAngleOffset);
        } catch (Exception ignored) {}
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
        if (Double.isNaN(startingAngle) && camera.hasResult() || Math.abs(getEstimatedAngle()) < 2 && !(Math.abs(camera.getAngleToGoalDegrees()) < 2)) {
            startingAngle = camera.getAngleToGoalDegrees();
            startingGyroRotation = driveTrain.getRotation();
        }

        if (!Double.isNaN(startingAngle)) {
            double turn = pidController.calculate(getEstimatedAngle(), 0);
            turn = Math.copySign(Math.min(MAX_OUTPUT, Math.abs(turn)), turn);
            driveTrain.tankDrive(-turn, turn);
        }
    }

    public double getEstimatedAngle() {
        return startingAngle
                + (driveTrain.getRotation() - startingGyroRotation);
    }

    public double getAngleOffset() {
        return (startingAngle
                + (driveTrain.getRotation() - startingGyroRotation)) - camera.getAngleToGoalDegrees();
    }

    @Override
    public boolean isFinished() {
        if (!camera.hasResult()) return false;
        if(pidController.atSetpoint() && Math.abs(camera.getAngleToGoalDegrees()) < 2) {
            driveTrain.stop();
            return true;
        }
        return false;
    }
}
