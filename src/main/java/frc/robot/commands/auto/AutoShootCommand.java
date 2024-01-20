// /* (C)2022 Max Niederman, Silas Gagnon, and contributors */
// package frc.robot.commands.auto;

// import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
// import frc.robot.commands.RunConveyorForSetTime;
// import frc.robot.commands.shooter.RunShooterWithCamera;
// import frc.robot.subsystems.Camera;
// import frc.robot.subsystems.Conveyor;
// import frc.robot.subsystems.Shooter;

// public class AutoShootCommand extends ParallelDeadlineGroup {
//     public AutoShootCommand(
//             Shooter shooter,
//             Conveyor conveyor,
//             Camera camera
//             ) {
//         super(
//                 new RunConveyorForSetTime(conveyor, Conveyor.Direction.Up, 1, 2.5),
//                 new RunShooterWithCamera(shooter, camera));
//     }
// }
