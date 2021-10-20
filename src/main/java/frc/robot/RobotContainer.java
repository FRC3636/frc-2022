// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.commands.SpinFlywheelsCommand;
import frc.robot.commands.StorageBeltsCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageBeltsSubsystem;

import java.util.Set;

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

    private final ArcadeDriveCommand arcadeDriveCommand = new ArcadeDriveCommand(driveTrainSubsystem);
    private final IntakeCommand intakeCommand = new IntakeCommand(intakeSubsystem);
    private final StorageBeltsCommand storageBeltsCommand = new StorageBeltsCommand(storageBeltsSubsystem);
    private final ClimbCommand climbCommand = new ClimbCommand(climbSubsystem);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveTrainSubsystem.setDefaultCommand(arcadeDriveCommand);
        climbSubsystem.setDefaultCommand(climbCommand);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        joystickLeft = new Joystick(Constants.Controls.JOYSTICK_LEFT);
        joystickRight = new Joystick(Constants.Controls.JOYSTICK_RIGHT);
        controller = new XboxController(Constants.Controls.XBOX_CONTROLLER);

        new Button(() -> controller.getYButton()).whileHeld(intakeCommand);
        new Button(() -> controller.getAButton()).whileHeld(new SpinFlywheelsCommand(shooterSubsystem, 0.5));
        new Button(() -> controller.getXButton()).whileHeld(new SpinFlywheelsCommand(shooterSubsystem, 0.75));
        new Button(() -> controller.getBButton()).whileHeld(new SpinFlywheelsCommand(shooterSubsystem, 1));
        new Button(() -> joystickRight.getTrigger()).whileHeld(storageBeltsCommand);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new Command() {
            @Override
            public Set<Subsystem> getRequirements() {
                return null;
            }
        };
    }
}
