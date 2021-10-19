package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;


public class ArcadeDriveCommand extends CommandBase {

    private final DriveTrainSubsystem m_driveTrainSubsystem;

    public ArcadeDriveCommand(DriveTrainSubsystem driveTrainSubsystem) {
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        this.m_driveTrainSubsystem = driveTrainSubsystem;
        addRequirements(driveTrainSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double speed = RobotContainer.leftJoystick.getY();
        double turn = RobotContainer.rightJoystick.getX();

        m_driveTrainSubsystem.runMotors((speed * Math.abs(speed)) + turn, (speed * Math.abs(speed)) - turn);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_driveTrainSubsystem.runMotors(0, 0);
    }
}
