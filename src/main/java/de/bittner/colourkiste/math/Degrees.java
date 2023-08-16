package de.bittner.colourkiste.math;

public record Degrees(double degrees) {
    public Radians toRadians() {
        return new Radians(degrees * Math.PI / 180.0);
    }

    public Degrees add(double angle) {
        return new Degrees(this.degrees + angle);
    }
}
