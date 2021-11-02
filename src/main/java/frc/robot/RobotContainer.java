/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.Constants.Autonomous;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.StorageBeltsSubsystem.Direction;

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
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final StorageBeltsSubsystem storageBeltsSubsystem = new StorageBeltsSubsystem();
  private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
  private final VisionSubsystem visionSubsystem = new VisionSubsystem();

  private final ArcadeDriveCommand arcadeDriveCommand = new ArcadeDriveCommand(driveTrainSubsystem);
  private final ClimbCommand climbCommand = new ClimbCommand(climbSubsystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();

    driveTrainSubsystem.setDefaultCommand(arcadeDriveCommand);
    climbSubsystem.setDefaultCommand(climbCommand);

    new ClimbBrakeCommand(climbSubsystem, ClimbSubsystem.BrakeState.Engaged).schedule();
  }

  private void configureButtonBindings() {
    joystickLeft = new Joystick(Constants.Controls.JOYSTICK_LEFT);
    joystickRight = new Joystick(Constants.Controls.JOYSTICK_RIGHT);
    controller = new XboxController(Constants.Controls.XBOX_CONTROLLER);

    new Button(() -> controller.getYButton())
        .whileHeld(new IntakeCommand(intakeSubsystem, IntakeSubsystem.Direction.In));
    new Button(() -> controller.getBumper(GenericHID.Hand.kLeft))
        .whileHeld(new IntakeCommand(intakeSubsystem, IntakeSubsystem.Direction.Out));
    new Button(() -> controller.getAButton())
        .whileHeld(new SpinFlywheelsCommand(shooterSubsystem, 0.5));
    new Button(() -> controller.getXButton())
        .whileHeld(new SpinFlywheelsCommand(shooterSubsystem, 0.7));
    new Button(() -> controller.getBButton())
        .whileHeld(new SpinFlywheelsCommand(shooterSubsystem, 1));
    new Button(() -> joystickRight.getTrigger()).whenHeld(new StorageBeltsCommand(storageBeltsSubsystem,
        Direction.Forward));
    new Button(() -> joystickLeft.getTrigger()).whenHeld(new StorageBeltsCommand(storageBeltsSubsystem,
        Direction.Backward));
    new Button(() -> controller.getBackButton())
        .whenPressed(new ClimbBrakeCommand(climbSubsystem, ClimbSubsystem.BrakeState.Released))
        .whenReleased(new ClimbBrakeCommand(climbSubsystem, ClimbSubsystem.BrakeState.Engaged));
    new Button(() -> joystickRight.getRawButton(Autonomous.AUTO_COMMAND_BUTTON))
        .whenHeld(new AutoShootCommand(driveTrainSubsystem, visionSubsystem));
  }

  public Command getAutonomousCommand() {
    return new AutoCommandGroup(driveTrainSubsystem, visionSubsystem, shooterSubsystem, storageBeltsSubsystem);
  }
}
