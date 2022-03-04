package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoCommand extends SequentialCommandGroup {
    public AutoCommand(DriveTrainSubsystem driveTrain, IntakeSubsystem intake, ConveyorSubsystem conveyor, ShooterSubsystem shooter) {
        super(
                new IntakePathFollowingCommand(driveTrain, intake, "test"),
                new AutoShootCommand(shooter, conveyor)
        );
    }
}
