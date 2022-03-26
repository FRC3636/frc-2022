package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.Direction;

public class IntakeCommand extends CommandBase {

    IntakeSubsystem intake;
    Direction direction;

    public IntakeCommand(IntakeSubsystem intake, Direction direction) {
        this.intake = intake;
        this.direction = direction;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.run(direction);
        intake.setIntakeDown();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
        intake.setIntakeUp();
    }
}
