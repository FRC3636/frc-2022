package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {

    IntakeSubsystem intake;
    double direction;

    public IntakeCommand(IntakeSubsystem intake, double direction) {
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
