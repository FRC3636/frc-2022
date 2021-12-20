/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.IntakeSubsystem.Direction;

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
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final WaterGunSubsystem waterGunSubsystem = new WaterGunSubsystem();

  private final ArcadeDriveCommand arcadeDriveCommand = new ArcadeDriveCommand(driveTrainSubsystem);
  private final ArmControlCommand armControlCommand = new ArmControlCommand(armSubsystem);

  /* The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();
     driveTrainSubsystem.setDefaultCommand(arcadeDriveCommand);
     armSubsystem.setDefaultCommand( armControlCommand);
  }

  private void configureButtonBindings() {
    joystickLeft = new Joystick(Constants.Controls.JOYSTICK_LEFT);
    joystickRight = new Joystick(Constants.Controls.JOYSTICK_RIGHT);
    controller = new XboxController(Constants.Controls.XBOX_CONTROLLER);

    new Button(() -> controller.getXButton()).whileHeld(new IntakeCommand(intakeSubsystem,
        Direction.IN));
    new Button(() -> controller.getYButton()).whileHeld(new IntakeCommand(intakeSubsystem,
        Direction.OUT));
//    new Button(() -> controller.getAButton()).whileHeld(new WaterGunCommand(waterGunSubsystem, true));
//    new Button(() -> controller.getBButton()).whileHeld(new WaterGunCommand(waterGunSubsystem, false));
//    new Button(() -> controller.getBumperPressed(Hand.kRight)).whileHeld(new ArmControlCommand(armSubsystem, true));
//    new Button(() -> controller.getBumperPressed(Hand.kLeft)).whileHeld(new ArmControlCommand(armSubsystem, false));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
