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
        double a = 3.31779;
        double b = 70.9701;
        double c = 3166.88;
        return a * distance * distance + b * distance + c;
    }

    public double getTopSpeedFromDist(Double distance) {
        double m = 662.528;
        double c = -300;
        double speed = m * distance + c;
        return speed > 5500 ? 5500 : speed;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
