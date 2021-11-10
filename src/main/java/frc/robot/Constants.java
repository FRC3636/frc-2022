/* (C) 2021 Grant Generals, FRC Team 3636 */
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
  }

  public static final class Drivetrain {
    public static final int MOTOR_LEFT = 0;
    public static final int MOTOR_RIGHT = 1;

    public static final int RIGHT_ENCODER_PORT_1 = 2;
    public static final int RIGHT_ENCODER_PORT_2 = 3;
    public static final int LEFT_ENCODER_PORT_1 = 4;
    public static final int LEFT_ENCODER_PORT_2 = 5;
  }

  public static final class Telemetry {
    public static final int PULSES_PER_ROTATION = 1024;
    public static final double WHEEL_CIRCUMFERENCE = 47.8536;

    public static final double DRIVE_TRAIN_WIDTH = 55.245;
  }
}
