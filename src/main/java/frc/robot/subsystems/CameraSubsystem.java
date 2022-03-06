package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;

public class CameraSubsystem extends SubsystemBase {

    private final PhotonCamera camera = new PhotonCamera("photonvision");
    private double distanceToGoal = 0;

    private double angleToGoal = 0;

    NetworkTable light = NetworkTableInstance.getDefault().getTable("vision");

    @Override
    public void periodic() {
        PhotonPipelineResult result = camera.getLatestResult();
        if(result.hasTargets()) {
            distanceToGoal = PhotonUtils.calculateDistanceToTargetMeters(
                    Constants.Camera.CAMERA_HEIGHT_METERS,
                    Constants.Camera.GOAL_HEIGHT_METERS,
                    Constants.Camera.CAMERA_PITCH_RADIANS,
                    Units.degreesToRadians(result.getBestTarget().getPitch())
            );

            angleToGoal = result.getBestTarget().getYaw();
        }
        else {
            distanceToGoal = 0;
            angleToGoal = 0;
        }
    }

    public double getDistanceToGoal() {
        return distanceToGoal;
    }

    public double getAngleToGoalDegrees() {
        return Units.radiansToDegrees(angleToGoal);
    }

    public void turnOnLight() {
        light.getEntry("vision").setBoolean(true);
    }

    public void turnOffLight() {
        light.getEntry("vision").setBoolean(false);
    }
}
