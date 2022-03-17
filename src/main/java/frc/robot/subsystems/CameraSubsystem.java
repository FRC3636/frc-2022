/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class CameraSubsystem extends SubsystemBase {

    private PhotonCamera camera;

    private double distanceToGoal = 0;
    private double angleToGoal = 0;
    private boolean hasResult = false;

    private Socket lightSocket = null;

    public CameraSubsystem() {
        super();
        camera = new PhotonCamera("goalvision");

        RobotContainer.cameraTab.addNumber("Angle", this::getAngleToGoalDegrees);
        RobotContainer.cameraTab.addNumber("Distance", this::getDistanceToGoal);
    }

    @Override
    public void periodic() {
        PhotonPipelineResult result = camera.getLatestResult();
        if (result.hasTargets()) {
            angleToGoal = 0;
            for (PhotonTrackedTarget target : result.getTargets()) {
                angleToGoal += target.getYaw();
            }
            angleToGoal /= result.getTargets().size();

            distanceToGoal =
                    PhotonUtils.calculateDistanceToTargetMeters(
                            Constants.Camera.CAMERA_HEIGHT_METERS,
                            Constants.Camera.GOAL_HEIGHT_METERS,
                            Constants.Camera.CAMERA_PITCH_RADIANS,
                            Units.degreesToRadians(result.getBestTarget().getPitch()));

            hasResult = true;
        } else {
            distanceToGoal = 0;
            angleToGoal = 0;
            hasResult = false;
        }
    }

    public double getDistanceToGoal() {
        return distanceToGoal;
    }

    public double getAngleToGoalDegrees() {
        return angleToGoal;
    }

    public boolean hasResult() {
        return hasResult;
    }

    public void initializeLight() throws UnknownHostException, IOException {
        if (lightSocket != null) {
            lightSocket = new Socket("photonvision", 4000);
        }
    }

    public void turnOnLight() {
        try {
            initializeLight();
            lightSocket.getOutputStream().write('0');
            lightSocket.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("WARN: failed to turn off camera light");
        }
    }

    public void turnOffLight() {
        try {
            initializeLight();
            lightSocket.getOutputStream().write('0');
            lightSocket.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("WARN: failed to turn off camera light");
        }
    }
}
