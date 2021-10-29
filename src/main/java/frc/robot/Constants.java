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

    public static final int JOYSTICK_SCROLL_WHEEL = 2;
    public static final int JOYSTICK_X_AXIS = 0;
    public static final int JOYSTICK_Y_AXIS = 1;
  }

  public static final class Drivetrain {

    public static final int MOTOR_RIGHT = 0;
    public static final int MOTOR_LEFT = 1;
  }

  public static final class Intake {

    public static final int MOTOR = 4;
  }

  public static final class Shooter {

    public static final int MOTOR_LEFT = 6;
    public static final int MOTOR_RIGHT = 7;
  }

  public static final class StorageBelts {

    public static final int BELT_MOTOR = 5;
  }

  public static final class Climb {

    public static final int CLIMB_MOTOR = 8;
    public static final int BRAKE_MOTOR = 3;

    public static final double BRAKE_SPEED = 0.75;

    public static final int BRAKE_POWER = 8;
    public static final double BRAKE_TIMEOUT = 0.5;
    public static final double BRAKE_OVERDRAW_THRESHOLD = 7.5;
  }

  public static final class Autonomous {
    public static final int AUTO_COMMAND_BUTTON = 2;

    public static final double SHOOT_DIST = 180;
    public static final double SHOOT_DIST_ERROR = 5;

    public static final double ANGLE_ERROR = 4;
    public static final int READING_LENGTH = 20;
  }
}
