/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDrive extends CommandBase {

    private final DriveTrain driveTrain;

    public ArcadeDrive(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        double speed = RobotContainer.joystickLeft.getY();
        double turn = RobotContainer.joystickRight.getX();

        double speedSensitivity = 1;
        double turnSensitivity = 2.9;


        if(RobotContainer.joystickLeft.getRawButton(3)) {
            driveTrain.stop();
        }
        else {
            driveTrain.arcadeDrive(speed / speedSensitivity, turn / turnSensitivity);
        }
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
