/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

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

    public static final double TRACK_WIDTH = 55.245;
    public static final DifferentialDriveKinematics KINEMATICS =
        new DifferentialDriveKinematics(TRACK_WIDTH);

    public static final double MAX_VELOCITY = 0.0;
    public static final double MAX_ACCELERATION = 0.0;

    // scary magic numbers found by frc-characterization
    public static final double KS = 0.0;
    public static final double KV = 0.0;
    public static final double KA = 0.0;
    public static final double KP = 0.0; // this has to be tuned

    public static final double RAMSETE_B = 0.0;
    public static final double RAMSETE_ZETA = 0.0;

    public static final TrajectoryConfig TRAJECTORY_CONFIG =
        new TrajectoryConfig(MAX_VELOCITY, MAX_ACCELERATION)
            .setKinematics(KINEMATICS)
            .addConstraint(
                new DifferentialDriveVoltageConstraint(
                    new SimpleMotorFeedforward(KS, KV, KA), KINEMATICS, 10));
  }

  public static final class Intake {
    public static final int MOTOR = 2;
  }

  public static final class Arm {
    public static final int MOTOR = 3;
    public static final int ENCODER = 0;

    public static final double P = 0.0;
    public static final double I = 0.0;
    public static final double D = 0.0;
  }
}
