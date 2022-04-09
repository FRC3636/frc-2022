/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class PointAtGoalCommand extends CommandBase {

    private final CameraSubsystem camera;
    private final DriveTrainSubsystem driveTrain;

    private final PIDController pidController = new PIDController(Autonomous.TURN_KP, Autonomous.TURN_KI, Autonomous.TURN_KD);

    private Timer timer;

    private final int velocitySize = (int) (50 * Camera.LATENCY);

    private static NetworkTableEntry setTurn = RobotContainer.cameraTab.add("Turn Distance", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
//    private static NetworkTableEntry maxOutput = RobotContainer.cameraTab.add("Max Output", 0).withWidget(BuiltInWidgets.kTextView).getEntry();

    private double startingAngle = Float.NaN;
    private double startingGyroRotation = Float.NaN;

    public PointAtGoalCommand(DriveTrainSubsystem driveTrain, CameraSubsystem camera) {
        this.driveTrain = driveTrain;
        this.camera = camera;

        addRequirements(driveTrain);

        pidController.setTolerance(2, 0.5);

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
        startingAngle = Float.NaN;
        startingGyroRotation = Float.NaN;
    }

    private static final double MAX_OUTPUT = 1.5;

    @Override
    public void execute() {
        if(!(Math.abs(camera.getAngleToGoalDegrees()) < 2)) {
            double turn = pidController.calculate(camera.getAngleToGoalDegrees(), 0);
            turn = Math.copySign(Math.min(MAX_OUTPUT, Math.abs(turn)), turn);

            driveTrain.tankDriveVolts(-turn, turn);
        }
        else {
            driveTrain.stop();
        }

    }

    public void resetStartingAngle() {
        startingAngle = camera.getAngleToGoalDegrees();
        startingGyroRotation = driveTrain.getRotation();
        System.out.println("reset");
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
//        if(pidController.atSetpoint() && camera.hasResult()) {
//            return false;
//
//        }
        return false;
    }
}
