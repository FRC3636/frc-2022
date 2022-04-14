/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.RunConveyorForSetTime;
import frc.robot.commands.shooter.RunShooterWithDistance;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class AutoShootFromDistanceCommand extends ParallelDeadlineGroup {
    public AutoShootFromDistanceCommand(
            Shooter shooter,
            Conveyor conveyor,
            double distance) {
        super(
                new RunConveyorForSetTime(conveyor, Conveyor.Direction.Up, 1, 4),
                new RunShooterWithDistance(shooter, distance));
    }
}
