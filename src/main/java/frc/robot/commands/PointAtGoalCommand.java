package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class PointAtGoalCommand extends CommandBase {

    private CameraSubsystem camera;
    private DriveTrainSubsystem driveTrain;

    private PIDController pidController;

    public PointAtGoalCommand(DriveTrainSubsystem driveTrain) {
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return(camera.getAngleToGoalDegrees() < 3 && Math.abs(driveTrain.getWheelSpeeds().leftMetersPerSecond - driveTrain.getWheelSpeeds().rightMetersPerSecond) < 0.01);
    }
}
