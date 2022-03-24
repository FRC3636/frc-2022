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
        double b = -200.17;
        double a = 56.062;
        double c = 3280.36;
        return a * distance * distance + b * distance + c;
    }

    public double getTopSpeedFromDist(Double distance) {
        double m = 685.461;
        double c = -242.857;
        double speed = m * distance + c;
        return speed > 5700 ? 5700 : speed;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}
