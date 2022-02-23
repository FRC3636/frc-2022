// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

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
        public static final int MOTOR_RIGHT = 2;
        public static final int MOTOR_LEFT = 1;

        public static final double SENSOR_UNITS_PER_REV = 2048;
        public static final double WHEEL_DIAMETER = 6;
        public static final double GEAR_RATIO = 10.71;
        public static final double SENSOR_UNITS_PER_METER = SENSOR_UNITS_PER_REV / GEAR_RATIO * (Math.PI * WHEEL_DIAMETER * 0.0254);

        public static final double DRIVE_VELOCITY_KP = 0;

        public static final double TRACK_WIDTH = 19 * 0.0254;
        public static final DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACK_WIDTH);

        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;

        public static final double FEED_FORWARD_KS = 0;
        public static final double FEED_FORWARD_KV = 0;
        public static final double FEED_FORWARD_KA = 0;
    }

    public static final class Shooter {
        public static final int BOTTOM = 8;
        public static final int TOP = 9;
        public static final double VELOCITY_TO_RPM = 600 / 2048f;

        public static final double TOP_P = 0.025f;
        public static final double TOP_I = 0.0001f;
        public static final double TOP_D = 0f;
        public static final double TOP_F =  ((0.5 * 1023) / 20000.0);
        public static final double BOTTOM_P = 0.025f;
        public static final double BOTTOM_I = 0.0001f;
        public static final double BOTTOM_D = 0f;
        public static final double BOTTOM_F = ((0.5 * 1023) / 20000.0);

        public static final int LOW_GOAL_BOTTOM_FENDER_SPEED = 800;
        public static final int LOW_GOAL_TOP_FENDER_SPEED = 2000;

    }

    public static final class Intake {
        public static final int MOTOR = 7;
        public static final int WINCH_MOTOR = 1;
    }

    public static final class Conveyor {
        public static final int MOTOR = 0;
    }

    public static final class Climb {
        public static final int TELESCOPING_MOTOR = 5;
        public static final int PIVOTING_MOTOR = 6;
    }
}
