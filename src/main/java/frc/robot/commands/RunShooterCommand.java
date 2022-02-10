package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Shooter;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterCommand extends CommandBase {

    private final ShooterSubsystem shooter;

    NetworkTableEntry topShooterSpeed, bottomShooterSpeed;

    public RunShooterCommand(ShooterSubsystem shooter, NetworkTableEntry bottomShooterSpeed, NetworkTableEntry topShooterSpeed) {
        this.shooter = shooter;
        addRequirements(shooter);
        this.bottomShooterSpeed = bottomShooterSpeed;
        this.topShooterSpeed = topShooterSpeed;
    }

    @Override
    public void execute() {
        shooter.run(
                ((int)(((RobotContainer.joystickLeft.getZ() + 1) / 2) * 70)) * 100 / Shooter.VELOCITY_TO_RPM,
                ((int)(((RobotContainer.joystickRight.getZ() + 1) / 2) * 70)) * 100 / Shooter.VELOCITY_TO_RPM);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
