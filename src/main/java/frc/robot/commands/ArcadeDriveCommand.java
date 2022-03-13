/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;

public class ArcadeDriveCommand extends CommandBase {

    private final DriveTrainSubsystem driveTrain;

    public ArcadeDriveCommand(DriveTrainSubsystem driveTrain) {
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        driveTrain.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)));
    }

    @Override
    public void execute() {
        double speed = RobotContainer.joystickLeft.getY();
        double turn = RobotContainer.joystickRight.getX();

        double speedSensitivity = 1;
        double turnSensitivity = 2.889;

        driveTrain.arcadeDrive(speed / speedSensitivity, turn / turnSensitivity);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.stop();
    }
}
