/* (C)2022 Max Niederman, Silas Gagnon, and contributors */
package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;

public class RunShooterWithDistance extends RunShooter {

    protected double distance;

    public RunShooterWithDistance(Shooter shooter, double distance) {
        super(shooter);
        this.distance = distance;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.run(getBottomSpeedFromDist(distance), getTopSpeedFromDist(distance));
    }

    public double getBottomSpeedFromDist(Double distance) {
        double b = -255.684;
        double a = 57.0426;
        double c = 3396.24;
        return a * distance * distance + b * distance + c;
    }

    public double getTopSpeedFromDist(Double distance) {
        double m = 620.007;
        double c = -43.571;
        double speed = m * distance + c;
        return speed > 5500 ? 5500 : speed;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
