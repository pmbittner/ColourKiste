package de.bittner.colourkiste.math;

public record Radians(double radians) {
    public Degrees toDegrees() {
        return new Degrees(radians * 180 / Math.PI);
    }

    public Radians add(double rad) {
        return new Radians(this.radians + rad);
    }
}
