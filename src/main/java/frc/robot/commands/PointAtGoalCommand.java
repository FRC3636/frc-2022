package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
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
//         RobotContainer.autoTab.add("Auto Aim PID", pidController).withWidget(BuiltInWidgets.kPIDController);

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
        double fakeAngle = Math.copySign(Math.min(Math.abs(camera.getAngleToGoalDegrees()), 10)
                , camera.getAngleToGoalDegrees());

        System.out.println("PID: " + pidController.getP() + ", " + pidController.getI() + ", " + pidController.getD());

        System.out.println("Angle: " + camera.getAngleToGoalDegrees() + ", Fake Angle: " + fakeAngle);

        double turn = pidController.calculate(fakeAngle, 0);

        System.out.println("Output: " + turn);

        driveTrain.tankDrive(-turn, turn);
    }

    @Override
    public boolean isFinished() {
        if (!camera.hasResult()) return false;
        if(timer.hasElapsed(4)) {
            return true;
        }
        boolean done = Math.abs(camera.getAngleToGoalDegrees()) < 3 &&
                Math.abs(driveTrain.getWheelSpeeds().leftMetersPerSecond) < 0.001 &&
                Math.abs(driveTrain.getWheelSpeeds().rightMetersPerSecond) < 0.001;
        System.out.println(done);

        return done;
//        return false;
    }
}
