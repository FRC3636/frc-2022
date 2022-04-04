/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
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

    private final NetworkTableEntry distance, angle;

    private Socket lightSocket = null;

    public CameraSubsystem() {
        super();
        distance = RobotContainer.cameraTable.getEntry("distance");
        angle = RobotContainer.cameraTable.getEntry("angle");

        RobotContainer.cameraTab.addNumber("Angle", this::getAngleToGoalDegrees);
        RobotContainer.cameraTab.addNumber("Distance", this::getDistanceToFeet);
    }


    public double getDistanceToGoal() {
        return distance.getDouble(0) - Units.feetToMeters(1);
    }

    public double getDistanceToFeet() {
        return Units.metersToInches(distance.getDouble(0));
    }

    public double getAngleToGoalDegrees() {
        return angle.getDouble(0);
    }

    public boolean hasResult() {
        return angle.getDouble(0) != 0;
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
}
