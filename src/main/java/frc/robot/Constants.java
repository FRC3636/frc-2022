// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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

        public static final int JOYSTICK_SCROLL_WHEEL = 2;
        public static final int JOYSTICK_X_AXIS = 0;
        public static final int JOYSTICK_Y_AXIS = 1;
    }

    public static final class Drivetrain {
        public static final int MOTOR_RIGHT = 0;
        public static final int MOTOR_LEFT = 1;
    }
}
