package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class RunIntakeWinchCommand extends CommandBase {

    private final IntakeSubsystem intake;
    private final IntakeSubsystem.Position winchDirection;

    public RunIntakeWinchCommand(IntakeSubsystem intake, IntakeSubsystem.Position winchDirection) {
        this.intake = intake;
        this.winchDirection = winchDirection;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.winch(winchDirection);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopWinch();
    }
}
