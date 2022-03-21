/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterPresetCommand extends RunShooterCommand {

    protected double topShooterSpeed, bottomShooterSpeed;

    public RunShooterPresetCommand(
            ShooterSubsystem shooter, int bottomShooterSpeed, int topShooterSpeed) {
        super(shooter);
        this.bottomShooterSpeed = bottomShooterSpeed;
        this.topShooterSpeed = topShooterSpeed;
    }

    @Override
    public void execute() {
        shooter.run(bottomShooterSpeed, topShooterSpeed);
        if(RobotContainer.controllerRumble.getBoolean(true) && !DriverStation.isAutonomous()) {
            if(Math.abs(shooter.getTopShooterSpeed() - topShooterSpeed) < 50 && Math.abs(shooter.getTopShooterSpeed() - topShooterSpeed) < 50) {
                RobotContainer.controller.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
                RobotContainer.controller.setRumble(GenericHID.RumbleType.kRightRumble, 1);
            }
            else {
                RobotContainer.controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
                RobotContainer.controller.setRumble(GenericHID.RumbleType.kRightRumble, 0);
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
