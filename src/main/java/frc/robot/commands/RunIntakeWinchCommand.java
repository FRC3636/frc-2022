package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class RunIntakeWinchCommand extends CommandBase {

    private final IntakeSubsystem intake;
    private final IntakeSubsystem.Position position;

    public RunIntakeWinchCommand(IntakeSubsystem intake, IntakeSubsystem.Position position) {
        this.intake = intake;
        this.position = position;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.winch(position);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopWinch();
    }
}
