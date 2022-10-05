package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class TankDrive extends CommandBase {

    private final DriveTrain driveTrain;

    public TankDrive(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        double speedL = RobotContainer.joystickLeft.getY();
        double speedR = RobotContainer.joystickRight.getY();

        driveTrain.tankDrive(speedL, speedR);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.stop();
    }
}
