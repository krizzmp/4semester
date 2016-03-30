package dk.sdu.group5.astarai;

public class Vec {
    double x;
    double y;

    public Vec(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec minus(Vec from) {
        return new Vec(x - from.x, y - from.y);
    }
}
