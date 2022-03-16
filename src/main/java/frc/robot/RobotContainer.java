/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.*;
import frc.robot.commands.auto.AutoAimShootCommand;
import frc.robot.commands.auto.AutoShootCommand;
import frc.robot.commands.auto.IntakePathFollowingCommand;
import frc.robot.commands.shooter.RunShooterPresetCommand;
import frc.robot.commands.shooter.RunShooterWithCameraCommand;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // Shuffleboard tabs
    public static final ShuffleboardTab driveSettings = Shuffleboard.getTab("Drive Settings");
    public static final ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
    public static final ShuffleboardTab autoTab = Shuffleboard.getTab("Auto");
    public static final ShuffleboardTab cameraTab = Shuffleboard.getTab("Camera");

    private static final NetworkTableEntry bottomShooterSpeed =
            shooterTab.add("Bottom Shooter", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    private static final NetworkTableEntry topShooterSpeed =
            shooterTab.add("Top Shooter", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();

    private static SendableChooser<String> autoModeChooser;
    private static SendableChooser<String> startingPositionChooser;

    public static Joystick joystickLeft;
    public static Joystick joystickRight;
    public static XboxController controller;

    private static final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
    private static final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    private static final ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();
    private static final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
    private static final CameraSubsystem cameraSubsystem = new CameraSubsystem();

    private static final ArcadeDriveCommand arcadeDriveCommand =
            new ArcadeDriveCommand(driveTrainSubsystem);

    /** The container for the robot. Contains subsystems, I/O devices, and commands. */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveTrainSubsystem.setDefaultCommand(arcadeDriveCommand);

        startingPositionChooser = new SendableChooser<String>();
        startingPositionChooser.addOption("Left", "left");
        startingPositionChooser.addOption("Middle", "left");
        startingPositionChooser.addOption("Right", "right");
        autoTab.add("Starting Position", startingPositionChooser)
                .withWidget(BuiltInWidgets.kComboBoxChooser);

        autoModeChooser = new SendableChooser<String>();
        autoModeChooser.addOption("One Ball", "one_ball");
        autoModeChooser.addOption("Two Balls", "two_ball");
        autoModeChooser.addOption("Radial", "radial");
        autoModeChooser.setDefaultOption("Radial", "radial");
        autoTab.add("Mode", autoModeChooser).withWidget(BuiltInWidgets.kComboBoxChooser);
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

        new Button(() -> controller.getAButton())
                .whileHeld(new RunShooterWithCameraCommand(shooterSubsystem, cameraSubsystem));
        new Button(() -> controller.getBButton())
                .whileHeld(
                        new RunShooterPresetCommand(
                                shooterSubsystem, 1700, 700)); // low hub from fender
        new Button(() -> controller.getXButton())
                .whileHeld(
                        new RunShooterPresetCommand(
                                shooterSubsystem, 3200, 500)); // high hub from fender

        new Button(() -> joystickRight.getTrigger())
                .whileHeld(
                        new RunConveyorCommand(conveyorSubsystem, ConveyorSubsystem.Direction.Up));
        new Button(() -> joystickLeft.getTrigger())
                .whileHeld(
                        new RunConveyorCommand(
                                conveyorSubsystem, ConveyorSubsystem.Direction.Down));
        new Button(() -> joystickLeft.getRawButton(6)).whenPressed(conveyorSubsystem::runAutoIndex);
        new Button(() -> joystickLeft.getRawButton(7)).whenPressed(conveyorSubsystem::stopAutoIndex);

        new Button(() -> controller.getYButton())
                .toggleWhenPressed(new ClimbCommand(climbSubsystem));

        new Button(() -> joystickRight.getRawButton(2))
                .whileHeld(new PointAtGoalCommand(driveTrainSubsystem, cameraSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        switch (autoModeChooser.getSelected()) {
            case "one_ball":
                return new AutoAimShootCommand(
                        shooterSubsystem, conveyorSubsystem, cameraSubsystem, driveTrainSubsystem);

            case "two_ball":
                return new SequentialCommandGroup(
                        new IntakePathFollowingCommand(
                                driveTrainSubsystem,
                                intakeSubsystem,
                                String.format(
                                        "two_ball.%s", startingPositionChooser.getSelected())),
                        new AutoAimShootCommand(
                                shooterSubsystem,
                                conveyorSubsystem,
                                cameraSubsystem,
                                driveTrainSubsystem));

            case "radial":
                return new SequentialCommandGroup(
                        new IntakePathFollowingCommand(
                                driveTrainSubsystem, intakeSubsystem, "radial"),
                        new AutoShootCommand(
                                shooterSubsystem,
                                conveyorSubsystem,
                                cameraSubsystem,
                                driveTrainSubsystem));

            default:
                return null;
        }
    }
}
