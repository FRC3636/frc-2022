/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.shooter;

import frc.robot.subsystems.ShooterSubsystem;

public class RunShooterWithDistanceCommand extends RunShooterCommand {

    protected double distance;

    public RunShooterWithDistanceCommand(ShooterSubsystem shooter, double distance) {
        super(shooter);
        this.distance = distance;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.run(getBottomSpeedFromDist(distance), getTopSpeedFromDist(distance));
    }

    public double getBottomSpeedFromDist(Double distance) {
        return (-355.5 * distance) + 3200;
    }

    public double getTopSpeedFromDist(Double distance) {
        double speed = (1200 * Math.pow(distance, 1.5)) + 500;
        return speed > 5700 ? 5700 : speed;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
