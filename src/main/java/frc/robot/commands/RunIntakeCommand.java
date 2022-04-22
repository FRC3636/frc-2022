package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class RunIntakeCommand extends CommandBase {

    Intake intake;
    double direction;

    public RunIntakeCommand(Intake intake, double direction) {
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
