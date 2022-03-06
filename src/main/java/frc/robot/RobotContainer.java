// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.*;
import frc.robot.commands.auto.AutoShootCommand;
import frc.robot.commands.auto.FollowTrajectoryCommand;
import frc.robot.commands.auto.IntakePathFollowingCommand;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static Joystick joystickLeft;
  public static Joystick joystickRight;
  public static XboxController controller;
  private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();
  private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
  private final CameraSubsystem cameraSubsystem = new CameraSubsystem();

  private final ArcadeDriveCommand arcadeDriveCommand = new ArcadeDriveCommand(driveTrainSubsystem);

  private ShuffleboardTab tab = Shuffleboard.getTab("Shooter");

  private NetworkTableEntry bottomShooterSpeed = tab.add("Bottom Shooter", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
  private NetworkTableEntry topShooterSpeed = tab.add("Top Shooter", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    driveTrainSubsystem.setDefaultCommand(arcadeDriveCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    joystickLeft = new Joystick(Constants.Controls.JOYSTICK_LEFT);
    joystickRight = new Joystick(Constants.Controls.JOYSTICK_RIGHT);
    controller = new XboxController(Constants.Controls.XBOX_CONTROLLER);

    new Button(() -> controller.getRightBumper())
            .whileHeld(new IntakeCommand(intakeSubsystem, IntakeSubsystem.Direction.In));
    new Button(() -> controller.getLeftBumper())
            .whileHeld(new IntakeCommand(intakeSubsystem, IntakeSubsystem.Direction.Out));

    // Shooter
    new Button(() -> controller.getAButton()).whileHeld(new RunShooterCommand(shooterSubsystem, bottomShooterSpeed, topShooterSpeed, cameraSubsystem));
    new Button(() -> controller.getBButton()).whileHeld(new RunShooterPresetCommand(shooterSubsystem, Constants.Shooter.LOW_GOAL_BOTTOM_FENDER_SPEED, Constants.Shooter.LOW_GOAL_TOP_FENDER_SPEED));

    new Button(() -> joystickRight.getTrigger()).whileHeld(new RunConveyorCommand(conveyorSubsystem, ConveyorSubsystem.Direction.Up));
    new Button(() -> joystickLeft.getTrigger()).whileHeld(new RunConveyorCommand(conveyorSubsystem, ConveyorSubsystem.Direction.Down));

    new Button(() -> controller.getYButton()).toggleWhenPressed(new ClimbCommand(climbSubsystem));

    new Button(() -> controller.getPOV() >= 315 || (controller.getPOV() <= 45 && controller.getPOV()>= 0)).whileHeld(new RunIntakeWinchCommand(intakeSubsystem, IntakeSubsystem.Position.Up));
    new Button(() -> controller.getPOV() <= 225 && controller.getPOV() >= 135).whileHeld(new RunIntakeWinchCommand(intakeSubsystem, IntakeSubsystem.Position.Down));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    int balls = 2;
    switch (balls) {
      case 1:
        return new AutoShootCommand(shooterSubsystem, conveyorSubsystem);

      case 2:
        return new SequentialCommandGroup(
          new IntakePathFollowingCommand(driveTrainSubsystem, intakeSubsystem, "two_ball"),
          new AutoShootCommand(shooterSubsystem, conveyorSubsystem)
        );

      case 3:
        return new SequentialCommandGroup(
          new IntakePathFollowingCommand(driveTrainSubsystem, intakeSubsystem, "two_ball"),
          new AutoShootCommand(shooterSubsystem, conveyorSubsystem),
          new IntakePathFollowingCommand(driveTrainSubsystem, intakeSubsystem, "three_ball"),
          new AutoShootCommand(shooterSubsystem, conveyorSubsystem)
        );

      default:
        return null;
    }
  }
}
