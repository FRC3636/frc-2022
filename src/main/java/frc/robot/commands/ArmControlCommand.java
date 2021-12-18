package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;


public class ArmControlCommand extends CommandBase {

  private final ArmSubsystem armSubsystem;

  public ArmControlCommand(ArmSubsystem armSubsystem) {
    this.armSubsystem = armSubsystem;
    // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
    addRequirements(this.armSubsystem);
  }

  /**
   * The main body of a command.  Called repeatedly while the command is scheduled. (That is, it is
   * called repeatedly until {@link #isFinished()}) returns true.)
   */
  @Override
  public void execute() {
    double speed = RobotContainer.controller.getY(Hand.kLeft) * 0.25;
    System.out.println(speed);
    armSubsystem.set(speed);
  }
}
