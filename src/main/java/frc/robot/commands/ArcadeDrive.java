package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

public class ArcadeDrive extends CommandBase {

    private final DriveTrain driveTrain;
    NetworkTableEntry turnSensitivity, speedSensitivity;
    
    public ArcadeDrive(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        turnSensitivity = RobotContainer.sensitivityTab.add("turn sensitivity", 1).withWidget(BuiltInWidgets.kNumberBar).getEntry();
        speedSensitivity = RobotContainer.sensitivityTab.add("speed sensitivity", 1).withWidget(BuiltInWidgets.kNumberBar).getEntry();
    }

    @Override
    public void execute() {
        double speed = RobotContainer.joystickLeft.getY();
        double turn = RobotContainer.joystickRight.getX()*-1;

        System.out.println(speedSensitivity);

        driveTrain.arcadeDrive(speed * speedSensitivity.getDouble(1), turn * turnSensitivity.getDouble(1));
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
