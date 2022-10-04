package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.networktables.NetworkTable;

public class ArcadeDriveCommand extends CommandBase {

    private final DriveTrainSubsystem driveTrain;
    double turnSensitivity = 1;
    double speedSensitivity = 1;
    
    public ArcadeDriveCommand(DriveTrainSubsystem driveTrain) {
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        RobotContainer.sensitivityTab.add("turn sensitivity", turnSensitivity).withWidget(BuiltInWidgets.kNumberBar);
        RobotContainer.sensitivityTab.add("speed sensitivity", speedSensitivity).withWidget(BuiltInWidgets.kNumberBar);
    }

    @Override
    public void execute() {
        double speed = RobotContainer.joystickLeft.getY();
        double turn = RobotContainer.joystickRight.getX();

        System.out.println(speedSensitivity);
        

        //double speedSensitivity = RobotContainer.joystickLeft.getZ() + RobotContainer.speedSensitivity.getDouble(0) + 2;
        //double turnSensitivity = RobotContainer.joystickRight.getZ() + RobotContainer.turnSensitivity.getDouble(0) + 2;

       

        driveTrain.arcadeDrive(speed * speedSensitivity, turn * turnSensitivity);
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
