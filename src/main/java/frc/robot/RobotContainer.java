/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutoIndex;
import frc.robot.commands.PointAtGoal;
import frc.robot.commands.UnloadConveyor;
import frc.robot.commands.auto.AutoAimShootCommand;
import frc.robot.commands.auto.AutoPathFollowing;
import frc.robot.commands.auto.AutoShootFromDistanceCommand;
import frc.robot.commands.shooter.RunShooterPreset;
import frc.robot.commands.shooter.RunShooterWithCamera;
import frc.robot.commands.shooter.RunShooterWithDistance;
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
    public static final NetworkTable cameraTable =
            NetworkTableInstance.getDefault().getTable("Camera");

    private static final NetworkTableEntry bottomShooterSpeed =
            shooterTab.add("Bottom Shooter", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    private static final NetworkTableEntry topShooterSpeed =
            shooterTab.add("Top Shooter", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();

    // Drive settings
    public static final NetworkTableEntry controllerRumble =
            driveSettings.add("Rumble", true).withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    private static SendableChooser<String> autoModeChooser;
    private static SendableChooser<String> startingPositionChooser;
    private static NetworkTableEntry delay = autoTab.add("Delay", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    public static Field2d field = new Field2d();

    public static Joystick joystickLeft;
    public static Joystick joystickRight;
    public static XboxController controller;

    private static final DriveTrain driveTrain = new DriveTrain();
    private static final Shooter shooter = new Shooter();
    private static final Intake intake = new Intake();
    private static final Conveyor conveyor = new Conveyor();
    private static final Climb climb = new Climb();
    private static final Camera camera = new Camera();

    private static final ArcadeDrive ARCADE_DRIVE =
            new ArcadeDrive(driveTrain);

    /** The container for the robot. Contains subsystems, I/O devices, and commands. */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveTrain.setDefaultCommand(ARCADE_DRIVE);
        conveyor.setDefaultCommand(new AutoIndex(conveyor));

        // Auto choices
        startingPositionChooser = new SendableChooser<String>();
        startingPositionChooser.addOption("Left Edge", "left_edge");
        startingPositionChooser.addOption("Left", "left");
        startingPositionChooser.addOption("Right", "right");
        startingPositionChooser.addOption("Right Edge", "right_edge");
        autoTab.add("Starting Position", startingPositionChooser)
                .withWidget(BuiltInWidgets.kComboBoxChooser);

        autoModeChooser = new SendableChooser<String>();
        autoModeChooser.addOption("One Ball", "one_ball");
        autoModeChooser.addOption("Two Balls", "two_ball");
        autoModeChooser.addOption("Three Balls", "three_ball");
        autoModeChooser.addOption("Four Balls", "four_ball");
        autoModeChooser.addOption("Five Balls", "five_ball");
        autoModeChooser.addOption("Defensive", "defensive");
        autoModeChooser.addOption("Radial", "radial");
        autoModeChooser.setDefaultOption("Radial", "radial");
        autoTab.add("Mode", autoModeChooser).withWidget(BuiltInWidgets.kComboBoxChooser);

        autoTab.add("Field", field).withWidget(BuiltInWidgets.kField);

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

        // Shooter
        new Button(() -> controller.getAButton())
                .whileHeld(new RunShooterWithCamera(shooter, camera));
        new Button(() -> controller.getBButton())
                .whileHeld(
                        new RunShooterPreset(
                                shooter, 1700, 700)); // low hub from fender
        new Button(() -> controller.getXButton())
                .whileHeld(
                        new RunShooterWithDistance(
                                shooter, Units.feetToMeters(8))); // high
        // hub
        // from
        // fender

        // Conveyor
        new Button(() -> joystickRight.getTrigger())
                .whileHeld(
                        new RunCommand(() -> conveyor.run(Conveyor.Direction.Up), conveyor));
        new Button(() -> joystickLeft.getTrigger())
                .whileHeld(
                        new RunCommand(() -> conveyor.run(Conveyor.Direction.Down), conveyor));

        new Button(() -> joystickLeft.getRawButton(2))
                .whenPressed(() -> conveyor.setDefaultCommand(null)).whenReleased(() -> conveyor.setDefaultCommand(new AutoIndex(conveyor)));

        // Intake
        new Button(() -> controller.getRightBumper())
                .whileHeld(new RunCommand(() -> intake.run(1), intake));
        new Button(() -> controller.getLeftBumper())
                .whileHeld(new RunCommand(() -> intake.run(-1), intake));


        // Intake Actuation
        new Button(
                        () ->
                                controller.getPOV() >= 315
                                        || (controller.getPOV() <= 45 && controller.getPOV() >= 0))
                .whileHeld(() ->
                {
                    intake.setIntakeUp();
                    intake.setIntakeLocked(false);
                });
        new Button(() -> controller.getPOV() <= 225 && controller.getPOV() >= 135)
                .whileHeld(() ->
                {
                    intake.setIntakeDown();
                    intake.setIntakeLocked(true);
                });

        // Climb
        new Button(() -> controller.getYButton())
                .toggleWhenPressed(new RunCommand(() -> climb.runClimb(RobotContainer.controller.getLeftY(), RobotContainer.controller.getRightX()), climb));

        // Auto Aiming
        new Button(() -> joystickRight.getRawButton(2))
                .whileHeld(new PointAtGoal(driveTrain, camera));

        new Button(() -> joystickRight.getRawButton(4))
                .whileHeld(new AutoAimShootCommand(shooter, conveyor, camera, driveTrain));

        new Button(() -> joystickLeft.getRawButton(4))
                .whenPressed(new UnloadConveyor(conveyor, shooter, camera));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        switch (autoModeChooser.getSelected()) {
            case "one_ball":
                return new SequentialCommandGroup(
                        new WaitCommand(delay.getDouble(0)),
                        new AutoAimShootCommand(shooter, conveyor, camera, driveTrain));

            case "two_ball":
                return new SequentialCommandGroup(
                        new WaitCommand(delay.getDouble(0)),
                        new AutoPathFollowing(
                                driveTrain,
                                intake,
                                shooter,
                                camera,
                                String.format(
                                        "two_ball.%s", startingPositionChooser.getSelected()), true),
                        new AutoAimShootCommand(
                                shooter,
                                conveyor,
                                camera,
                                driveTrain));
            case "three_ball":
                return new SequentialCommandGroup(
                        new WaitCommand(delay.getDouble(0)),
                        new AutoAimShootCommand(
                                shooter,
                                conveyor,
                                camera,
                                driveTrain),
                        new AutoPathFollowing(
                                driveTrain,
                                intake,
                                shooter,
                                camera,
                            String.format(
                                    "three_ball.%s", startingPositionChooser.getSelected()), false),
                        new AutoAimShootCommand(
                                shooter,
                                conveyor,
                                camera,
                                driveTrain)
                );
            case "four_ball":
                return new SequentialCommandGroup(
                        new WaitCommand(delay.getDouble(0)),
                        new AutoPathFollowing(
                                driveTrain,
                                intake,
                                shooter,
                                camera,
                                String.format(
                                        "two_ball.%s", startingPositionChooser.getSelected()), true),
                        new AutoAimShootCommand(
                                shooter,
                                conveyor,
                                camera,
                                driveTrain),
                        new AutoPathFollowing(
                                driveTrain,
                                intake,
                                shooter,
                                camera,
                                String.format(
                                        "four_ball.%s", startingPositionChooser.getSelected()), false),
                        new AutoAimShootCommand(
                                shooter,
                                conveyor,
                                camera,
                                driveTrain));
            case "five_ball":
                driveTrain.resetOdometry(PathPlanner.loadPath(String.format(
                        "three_ball.%s", startingPositionChooser.getSelected()), 2, 1).getInitialPose());
                return new SequentialCommandGroup(
                        new WaitCommand(delay.getDouble(0)),
                        new AutoAimShootCommand(
                                shooter,
                                conveyor,
                                camera,
                                driveTrain),
                        new AutoPathFollowing(
                                driveTrain,
                                intake,
                                shooter,
                                camera,
                                String.format(
                                        "three_ball.%s", startingPositionChooser.getSelected()), false),
                        new AutoAimShootCommand(
                                shooter,
                                conveyor,
                                camera,
                                driveTrain),
                        new AutoPathFollowing(
                                driveTrain,
                                intake,
                                shooter,
                                camera,
                                String.format(
                                        "five_ball.%s", startingPositionChooser.getSelected()), false),
                        new ParallelCommandGroup(
                                new AutoAimShootCommand(
                                        shooter,
                                        conveyor,
                                        camera,
                                        driveTrain),
                                new RunCommand(() -> intake.run( -0.5), intake)
                                )
                );
        
            case "defensive":
                driveTrain.resetOdometry(PathPlanner.loadPath(String.format(
                        "defensive.%s", startingPositionChooser.getSelected()), 2, 1).getInitialPose());
                return new SequentialCommandGroup(
                        new WaitCommand(delay.getDouble(0)),
                        new AutoAimShootCommand(shooter, conveyor, camera, driveTrain),
                        new AutoPathFollowing(
                                driveTrain,
                                intake,
                                shooter,
                                camera,
                                String.format(
                                        "defensive.%s", startingPositionChooser.getSelected()), false, 2, 1),
                        new ParallelCommandGroup(
                                new RunCommand(() -> intake.run( -0.5), intake),
                                new RunCommand(() -> conveyor.run(Conveyor.Direction.Down), intake)
                        )
                );

            case "radial":
                return new SequentialCommandGroup(
                        new WaitCommand(delay.getDouble(0)),
                        new AutoPathFollowing(
                                driveTrain, intake, shooter,
                                camera,"" +
                                "radial", true, 3, 1),
                        new AutoShootFromDistanceCommand(
                                shooter, conveyor, 2.2));

            default:
                return null;
        }
    }
}
