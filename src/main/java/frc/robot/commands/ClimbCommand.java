package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase {

    private final ClimbSubsystem climb;

    public ClimbCommand(ClimbSubsystem climb) {
        this.climb = climb;
        addRequirements(climb);
    }

    @Override
    public void execute() {
        climb.runMotor(RobotContainer.controller.getLeftY());
    }

    @Override
    public void end(boolean interrupted) {
        climb.stop();
    }
}
