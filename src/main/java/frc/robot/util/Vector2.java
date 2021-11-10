package frc.robot.util;

import edu.wpi.first.wpilibj.drive.Vector2d;

public class Vector2 {
  public double x;
  public double y;

  public Vector2() {
    this.x = 0;
    this.y = 0;
  }
    
  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public Vector2 add(Vector2 other) {
    this.x += other.x;
    this.y += other.y;

    return this;
  }

  public Vector2 sub(Vector2 other) {
    this.x -= other.x;
    this.y -= other.y;

    return this;
  }

  public Vector2 scl(double magnitude) {
    this.x *= magnitude;
    this.y *= magnitude;

    return this;
  }

  public Vector2 rotate(double angle) {
    double cosA = Math.cos(angle);
    double sinA = Math.sin(angle);

    Vector2 rotatedVec = new Vector2(0, 0);

    rotatedVec.x = x * cosA - y * sinA;
    rotatedVec.y = x * sinA + y * cosA;

    x = rotatedVec.x;
    y = rotatedVec.y;

    return this;
  }

  public Vector2 cpy() {
    return new Vector2(x, y);
  }
}
