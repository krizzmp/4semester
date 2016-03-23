package dk.sdu.group5.common.data;

public class Posf2d {
    private final float x;
    private final float y;

    public Posf2d(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Posf2d{" + "x=" + x + ", y=" + y + '}';
    }
}
