package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterCommand extends CommandBase {

    private final ShooterSubsystem shooter;

    NetworkTableEntry topShooterSpeed, bottomShooterSpeed;

    private final CameraSubsystem camera;

    public RunShooterCommand(ShooterSubsystem shooter, NetworkTableEntry bottomShooterSpeed, NetworkTableEntry topShooterSpeed, CameraSubsystem camera) {
        this.shooter = shooter;
        this.camera = camera;
        addRequirements(shooter, camera);
        this.bottomShooterSpeed = bottomShooterSpeed;
        this.topShooterSpeed = topShooterSpeed;
        camera.turnOnLight();
    }

    @Override
    public void execute() {
        shooter.run(
                bottomShooterSpeed.getDouble(0),
                topShooterSpeed.getDouble(0));

//        System.out.println(camera.getDistanceToGoal());
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        camera.turnOffLight();
    }
}
