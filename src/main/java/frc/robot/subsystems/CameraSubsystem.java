package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;

public class CameraSubsystem extends SubsystemBase {

    private final PhotonCamera camera = new PhotonCamera("goalvision");
    Socket lightSocket;

    private double distanceToGoal = 0;
    private double angleToGoal = 0;

    public CameraSubsystem() {
        super();

        try {
            lightSocket = new Socket("photonvision.local", 4000);
        } catch (Exception e) {
            System.err.println("ERROR: Could not connect to camera light server.");
        }
    }

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
        return angleToGoal;
    }

    public void turnOnLight() {
        try {
            lightSocket.getOutputStream().write('1');
        } catch (Exception e) {
            System.err.println("WARN: failed to turn on camera light");
        }
    }

    public void turnOffLight() {
        try {
            lightSocket.getOutputStream().write('0');
        } catch (Exception e) {
            System.err.println("WARN: failed to turn off camera light");
        }
    }
}
