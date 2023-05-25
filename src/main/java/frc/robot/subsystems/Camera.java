/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;

public class Camera extends SubsystemBase {

    private final PhotonCamera camera;
    private double distanceToGoal = 0;
    private double angleToGoal = 0;

    private Socket lightSocket = null;

    public Camera() {
        RobotContainer.cameraTab.addNumber("Angle (deg)", this::getAngleToGoalDegrees);
        RobotContainer.cameraTab.addNumber("Distance (in)", this::getDistanceToGoalInches);

        try {
            initializeLight();
        } catch (IOException e) {
            e.printStackTrace();
        }

        camera = new PhotonCamera("goalvision");
        camera.setDriverMode(false);
    }

    @Override
    public void periodic() {
        PhotonPipelineResult result = camera.getLatestResult();
        if (result.hasTargets()) {
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
        return 0;
    }

    public double getDistanceToGoalInches() {
        return Units.metersToInches(getDistanceToGoal());
    }

    public double getAngleToGoalDegrees() {
        return angleToGoal;
    }

    public boolean hasTarget() {
        return camera.getLatestResult().hasTargets();
    }

    public void initializeLight() throws UnknownHostException, IOException {
        System.out.println("connecting");
        lightSocket = new Socket("10.36.36.11", 4000);
    }

    public void turnOnLight() {
        try {
            lightSocket.getOutputStream().write('1');
            lightSocket.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                initializeLight();
                lightSocket.getOutputStream().write('1');
                lightSocket.getOutputStream().flush();
            } catch (Exception e1) {
                System.err.println("WARN: failed to init light");
            }
            System.err.println("WARN: failed to turn off camera light");
        }
    }

    public void turnOffLight() {
        try {
            lightSocket.getOutputStream().write('0');
            lightSocket.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("WARN: failed to turn off camera light");
        }
    }

    public boolean underThreshold() {
        return Math.abs(getAngleToGoalDegrees()) < 2;
    }
}
