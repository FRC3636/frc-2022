/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class Controls {
        public static final int JOYSTICK_LEFT = 0;
        public static final int JOYSTICK_RIGHT = 1;
        public static final int XBOX_CONTROLLER = 2;
    }

    public static final class Drivetrain {
        public static final int MOTOR_RIGHT_1 = 1;
        public static final int MOTOR_RIGHT_2 = 2;
        public static final int MOTOR_LEFT_1 = 3;
        public static final int MOTOR_LEFT_2 = 4;

        public static final double SENSOR_UNITS_PER_REV = 2048;
        public static final double WHEEL_DIAMETER = 6;
        public static final double GEAR_RATIO = 10.71;
        public static final double WHEEL_CIRCUMFERENCE = (Math.PI * WHEEL_DIAMETER * 0.0254);
        public static final double SENSOR_UNITS_PER_METER =
                (SENSOR_UNITS_PER_REV * GEAR_RATIO) / WHEEL_CIRCUMFERENCE;

        public static final double DRIVE_VELOCITY_KP = 5;

        public static final double TRACK_WIDTH = 19 * 0.0254;
        public static final DifferentialDriveKinematics KINEMATICS =
                new DifferentialDriveKinematics(TRACK_WIDTH);

        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;

        public static final double FEED_FORWARD_KS = 0.75495;
        public static final double FEED_FORWARD_KV = 2.3071;
        public static final double FEED_FORWARD_KA = 0.40615;
    }

    public static final class Shooter {
        public static final int BOTTOM = 8;
        public static final int TOP = 9;
        public static final double VELOCITY_TO_RPM = 600 / 2048f;

        public static final double TOP_P = 0.01;
        public static final double TOP_I = 0.00002;
        public static final double TOP_D = 0.001;
        public static final double TOP_F = ((0.5 * 1023) / 11000.0);
        public static final double BOTTOM_P = 0.04;
        public static final double BOTTOM_I = 0.00001;
        public static final double BOTTOM_D = 0.001;
        public static final double BOTTOM_F = ((0.5 * 1023) / 11000.0);
    }

    public static final class Intake {
        public static final int MOTOR = 10;
        public static final int ACTUATION_MOTOR = 12;

        public static final int ACTUATION_LIMIT_SWITCH = 5;
        public static final float ACTUATION_MOTOR_GEAR_RATIO = 9f * (35f / 22f) * (28f / 19f);
        public static final double ACTUATION_DEGREES = 56;
    }

    public static final class Conveyor {
        public static final int MOTOR = 13;
        public static final double CURRENT_THRESHOLD = 5
                ;
    }

    public static final class Climb {
        public static final int RIGHT_TELESCOPING_MOTOR = 5;
        public static final int LEFT_TELESCOPING_MOTOR = 6;
        public static final int RIGHT_PIVOT_MOTOR = 10;
        public static final int LEFT_PIVOT_MOTOR = 11;

        public static final int PIVOT_LIMIT_SWITCH_OUT = 3;
        public static final int PIVOT_LIMIT_SWITCH_IN = 4;
    }

    public static final class Camera {
        public static final double LATENCY = 0.05;
    }

    public static final class Autonomous {
        public static final double TURN_KP = 0.3;
        public static final double TURN_KI = 0;
        public static final double TURN_KD = 0.01;
    }
}
