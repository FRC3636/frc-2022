package frc.robot;

import java.util.HashMap;
import java.util.Map;

public class DriveConfig {
    private static final Map<String, DriveConfig> PRESETS = new HashMap<>();

    static {
        PRESETS.put("jude", new DriveConfig(1, 2.9));
        PRESETS.put("person_2", new DriveConfig(0.7, 2));
    }

    public static DriveConfig getPreset(String string) {
        return PRESETS.get(string);
    }

    public static DriveConfig getCurrent() {
        // may be bad to get shuffleboard values constantly
        return PRESETS.getOrDefault(RobotContainer.drivePresetsChooser.getSelected(), PRESETS.get("jude"));
    }

    private final double speedSensitivity;

    private final double turnSensitivity;
    public DriveConfig(double speedSensitivity, double turnSensitivity) {
        this.speedSensitivity = speedSensitivity;
        this.turnSensitivity = turnSensitivity;
    }

    public double getSpeedSensitivity() {
        return speedSensitivity;
    }

    public double getTurnSensitivity() {
        return turnSensitivity;
    }
}
