/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class AutoIndexCommand extends CommandBase {
    private final ConveyorSubsystem conveyor;
    private boolean enabled = true;

    DigitalInput input = new DigitalInput(1);
    private boolean lastInput = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AutoIndexCommand(ConveyorSubsystem conveyor) {
        this.conveyor = conveyor;
        addRequirements(this.conveyor);
    }

    public void execute() {
        if (this.enabled) {
            if (!input.get()) {
                conveyor.run(ConveyorSubsystem.Direction.Up);
            } else if (lastInput) {
                conveyor.stop();
            }
        }
        lastInput = input.get();
    }

    public void end(boolean interrupted) {
        conveyor.stop();
    }
}
