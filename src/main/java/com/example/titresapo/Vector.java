package com.example.titresapo;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void normalize() {
        double mag = Math.sqrt(this.x * this.x + this.y * this.y);
        double angle = Math.atan2(this.y, this.x);
        this.x = 1.0 * Math.cos(angle);
        this.y = 1.0 * Math.sin(angle);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setMag(int scalar) {
        this.x *= (double)scalar;
        this.y *= (double)scalar;
    }
}
