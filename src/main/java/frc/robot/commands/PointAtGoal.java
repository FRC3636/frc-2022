/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.DriveTrain;

import static frc.robot.Constants.Autonomous;

public class PointAtGoal extends CommandBase {

    private final Camera camera;
    private final DriveTrain driveTrain;

    private final PIDController pidController = new PIDController(Autonomous.TURN_KP, Autonomous.TURN_KI, Autonomous.TURN_KD);

    private static GenericEntry setTurn = RobotContainer.cameraTab.add("Turn Distance", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
//    private static NetworkTableEntry maxOutput = RobotContainer.cameraTab.add("Max Output", 0).withWidget(BuiltInWidgets.kTextView).getEntry();

    public PointAtGoal(DriveTrain driveTrain, Camera camera) {
        this.driveTrain = driveTrain;
        this.camera = camera;

        addRequirements(driveTrain);

        pidController.setTolerance(2, 0.5);

        try {
            RobotContainer.cameraTab.add("Auto Aim PID",
                    pidController).withWidget(BuiltInWidgets.kPIDController);
        } catch (Exception ignored) {}
    }

    @Override
    public void initialize() {
        camera.turnOnLight();
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

    @Override
    public boolean isFinished() {
        return false;
    }
}
