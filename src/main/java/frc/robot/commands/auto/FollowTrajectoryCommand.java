// /* (C)2022 Max Niederman, Silas Gagnon, and contributors */
// package frc.robot.commands.auto;

// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.controller.RamseteController;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Transform2d;
// import edu.wpi.first.math.trajectory.Trajectory;
// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.RamseteCommand;
// import frc.robot.RobotContainer;
// import frc.robot.subsystems.DriveTrain;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.nio.file.StandardOpenOption;
// import java.util.ArrayList;

// import static frc.robot.Constants.Drivetrain;

// public class FollowTrajectoryCommand extends RamseteCommand {
//     private final DriveTrain driveTrain;
//     private final ArrayList<PositionedCommand<Command>> positionedCommands;
//     private final Trajectory trajectory;

//     private boolean resetOdometry = true;

//     public FollowTrajectoryCommand(
//             DriveTrain driveTrain,
//             Trajectory trajectory,
//             ArrayList<PositionedCommand<Command>> positionedCommands) {
//         super(
//                 trajectory,
//                 driveTrain::getPose,
//                 new RamseteController(
//                         Drivetrain.RAMSETE_B, Drivetrain.RAMSETE_ZETA),
//                 new SimpleMotorFeedforward(
//                         Drivetrain.FEED_FORWARD_KS,
//                         Drivetrain.FEED_FORWARD_KV,
//                         Drivetrain.FEED_FORWARD_KA),
//                 Drivetrain.KINEMATICS,
//                 driveTrain::getWheelSpeeds,
//                 new PIDController(Drivetrain.DRIVE_VELOCITY_KP, 0, 0),
//                 new PIDController(Drivetrain.DRIVE_VELOCITY_KP, 0, 0),
//                 driveTrain::tankDriveVolts,
//                 driveTrain);

//         this.trajectory = trajectory;

//         this.driveTrain = driveTrain;
//         this.positionedCommands = positionedCommands;
//     }

//     public FollowTrajectoryCommand(DriveTrain driveTrain, Trajectory trajectory, boolean resetOdometry) {
//         this(driveTrain, trajectory, new ArrayList<PositionedCommand<Command>>(0));
//         this.resetOdometry = resetOdometry;

//         driveTrain.setNeutralMode(NeutralMode.Brake);
//     }

//     public void addPositionedCommand(PositionedCommand<Command> positionedCommand) {
//         positionedCommands.add(positionedCommand);
//     }

//     @Override
//     public void initialize() {
//         if(resetOdometry) {
//             driveTrain.resetOdometry(trajectory.getInitialPose());
//         }
//         super.initialize();

//         try {
//             Files.writeString(Paths.get("/home/lvuser/log.txt"), "", StandardOpenOption.CREATE);
//         } catch (IOException e) {
//             DriverStation.reportError("unable to create logfile " + e, true);
//         }
//     }

//     @Override
//     public void execute() {
//         super.execute();

//         Pose2d pose = driveTrain.getPose();

//         for (PositionedCommand<Command> command : positionedCommands) {
//             command.executeAt(pose);
//         }
//     }

//     @Override
//     public void end(boolean interrupted) {
//         driveTrain.stop();
//         driveTrain.setNeutralMode(NeutralMode.Coast);
//         super.end(interrupted);
//     }

//     public class PositionedCommand<C extends Command> {
//         private final C command;
//         private final Pose2d start;
//         private final Pose2d end;
//         private final double tolerance;
//         private boolean running = false;

//         public PositionedCommand(C command, Pose2d start, Pose2d end, double tolerance) {
//             this.command = command;
//             this.start = start;
//             this.end = end;
//             this.tolerance = tolerance;
//         }

//         public PositionedCommand(C command, Pose2d start, Pose2d end) {
//             this(command, start, end, 0.0);
//         }

//         public void executeAt(Pose2d pose) {
//             if (transformLength(start.minus(pose)) < tolerance) {
//                 running = true;
//                 command.initialize();
//             }

//             if (transformLength(end.minus(pose)) < tolerance) {
//                 running = false;
//                 command.end(true);
//             }

//             if (running) {
//                 command.execute();
//             }
//         }

//         double transformLength(Transform2d transform) {
//             return Math.sqrt(
//                     Math.pow(transform.getTranslation().getX(), 2)
//                             + Math.pow(transform.getTranslation().getY(), 2));
//         }
//     }
// }
