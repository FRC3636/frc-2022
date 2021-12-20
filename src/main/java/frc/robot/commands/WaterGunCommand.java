package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.WaterGun;
import frc.robot.subsystems.WaterGunSubsystem;

public class WaterGunCommand extends CommandBase {
  private final WaterGunSubsystem waterGun;
  private final boolean open;

  public WaterGunCommand(WaterGunSubsystem waterGun, boolean open) {
    this.waterGun = waterGun;
    this.open = open;
    addRequirements(waterGun);
  }

  @Override
  public void initialize() {
    if(open) {
      waterGun.open();
    }
    else {
      waterGun.close();
    }
  }
}
