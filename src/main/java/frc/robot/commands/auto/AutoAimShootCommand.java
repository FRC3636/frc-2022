/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.PointAtGoal;
import frc.robot.commands.UnloadConveyor;
import frc.robot.commands.shooter.RunShooterWithCamera;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class AutoAimShootCommand extends ParallelDeadlineGroup {
    public AutoAimShootCommand(
            Shooter shooter,
            Conveyor conveyor,
            Camera camera,
            DriveTrain driveTrain) {
        super(
                new UnloadConveyor(conveyor, shooter, camera).asProxy(),
                new PointAtGoal(driveTrain, camera),
                new RunShooterWithCamera(shooter, camera));
    }
}
