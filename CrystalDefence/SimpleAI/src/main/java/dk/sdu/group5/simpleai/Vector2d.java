package dk.sdu.group5.simpleai;

class Vector2d {
    final double x;
    final double y;

    Vector2d(double x, double y) {

        this.x = x;
        this.y = y;
    }

    Vector2d minus(Vector2d e) {
        return new Vector2d(x - e.x, y - e.y);
    }

    Vector2d times(float n) {
        return new Vector2d(x * n, y * n);
    }

    Vector2d unit() {
        return divide(length());
    }

    Vector2d divide(double length) {
        return new Vector2d(x / length, y / length);
    }

    double length() {
        return Math.sqrt(x * x + y * y);
    }

    Vector2d plus(Vector2d e) {
        return new Vector2d(x + e.x, y + e.y);
    }
}
