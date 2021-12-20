package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;


public class ArmControlCommand extends CommandBase {

  private final ArmSubsystem armSubsystem;
//  private final boolean up;

  public ArmControlCommand(ArmSubsystem armSubsystem) {
    this.armSubsystem = armSubsystem;
//    this.up = up;
    // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
    addRequirements(this.armSubsystem);
  }

//  @Override
//  public void initialize() {
//    armSubsystem.set(up? 1 : -1);
//  }
//
//  @Override
//  public void end(boolean interrupted) {
//    armSubsystem.set(0);
//  }

  @Override
  public void execute() {
    double speed = -Math.copySign(Math.pow(RobotContainer.controller.getY(Hand.kLeft), 2), RobotContainer.controller.getY(Hand.kLeft) * 0.75);
    System.out.println("Speed: " + speed);
    armSubsystem.set(speed);
  }
}
