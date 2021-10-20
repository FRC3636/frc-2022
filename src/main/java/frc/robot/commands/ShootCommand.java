package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterSubsystem;


public class ShootCommand extends CommandBase {
    private final ShooterSubsystem shooterSubsystem;

    public ShootCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.shooterSubsystem);
    }

    @Override
    public void initialize() {
        shooterSubsystem.spin(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.spin(0);
    }
}
