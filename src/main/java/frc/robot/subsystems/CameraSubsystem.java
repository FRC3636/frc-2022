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

    private PhotonCamera camera;

    private double distanceToGoal = 0;
    private double angleToGoal = 0;
    private boolean hasResult = false;

    public CameraSubsystem() {
        super();
        camera = new PhotonCamera("goalvision");
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
            hasResult = true;
        }
        else {
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
    };

    public void turnOnLight() {
//        try {
//            Socket lightSocket = new Socket("photonvision", 4000);
//            lightSocket.getOutputStream().write('1');
//            lightSocket.getOutputStream().flush();
//            lightSocket.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("WARN: failed to turn off camera light");
//        }
    }

    public void turnOffLight() {
//        try {
//            Socket lightSocket = new Socket("photonvision", 4000);
//            lightSocket.getOutputStream().write('0');
//            lightSocket.getOutputStream().flush();
//            lightSocket.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("WARN: failed to turn off camera light");
//        }
    }
}
