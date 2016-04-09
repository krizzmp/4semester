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

    public Vec plus(double x_, double y_) {
        return new Vec(x + x_, y + y_);
    }

    public double length() {
        return Math.sqrt(x*x+y*y);
    }

    public Vec plus(Vec d) {
        return new Vec(x + d.x, y + d.y);
    }


    Vec times(double n) {
        return new Vec(x * n, y * n);
    }

    Vec unit() {
        return divide(length());
    }

    Vec divide(double length) {
        return new Vec(x / length, y / length);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec vec = (Vec) o;

        return Double.compare(vec.x, x) == 0 && Double.compare(vec.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ temp >>> 32);
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%.0f, %.0f)",x,y);
    }

}
