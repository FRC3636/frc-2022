/* (C) 2021 Grant Generals, FRC Team 3636 */
package frc.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageBeltsSubsystem;
import frc.robot.subsystems.StorageBeltsSubsystem.Direction;

public class UnloadBallsCommand extends CommandBase {

  private final ShooterSubsystem shooter;
  private final StorageBeltsSubsystem belt;
  private Timer timer;


  public UnloadBallsCommand(ShooterSubsystem shooter, StorageBeltsSubsystem belt) {
    this.shooter = shooter;
    this.belt = belt;
    addRequirements(this.shooter);
  }

  @Override
  public void initialize() {
    timer = new Timer();
    timer.start();
  }

  @Override
  public void execute() {
    shooter.spin(Constants.Autonomous.AUTO_SHOOT_SPEED);
    if (timer.get() > 2){
      belt.runBelts(Direction.Forward);
    }
    
  }
  @Override
  public boolean isFinished() {
    return timer.get() > 7;
  }


  @Override
  public void end(boolean interrupted) {
    shooter.spin(0);
    belt.stopBelts();
  }
}
