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

    public PointAtGoalCommand(DriveTrainSubsystem driveTrain, CameraSubsystem camera) {
        this.driveTrain = driveTrain;
        this.camera = camera;

        pidController = new PIDController(0.0085, 0, 0.0004);
        RobotContainer.cameraTab.add("Auto Aim PID", pidController).withWidget(BuiltInWidgets.kPIDController);

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        camera.turnOnLight();
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
        // Cap angle at 10
        // double fakeAngle =
        //         Math.copySign(
        //                 Math.min(Math.abs(camera.getAngleToGoalDegrees()), 10),
        //                 camera.getAngleToGoalDegrees());

        System.out.println(pidController.getP());

        double turn = pidController.calculate(camera.getAngleToGoalDegrees(), 0) / 3;

        driveTrain.tankDrive(-turn, turn);
    }

    @Override
    public boolean isFinished() {
        if (!camera.hasResult()) return false;
        // if (timer.hasElapsed(4)) {
            // return true;
        // }

        return Math.abs(camera.getAngleToGoalDegrees()) < 3
                && Math.abs(driveTrain.getWheelSpeeds().leftMetersPerSecond) < 0.001
                && Math.abs(driveTrain.getWheelSpeeds().rightMetersPerSecond) < 0.001;
    }
}
