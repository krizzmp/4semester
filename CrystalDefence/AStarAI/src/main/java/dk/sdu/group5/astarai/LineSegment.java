package dk.sdu.group5.astarai;


import java.util.List;

public class LineSegment {
    private final Vec startPos;
    private final Vec direction;

    public LineSegment(Vec from, Vec to) {
        startPos = from;
        direction = to.minus(from);
    }

    public Vec getStartPos() {
        return startPos;
    }

    public Vec getDirection() {
        return direction;
    }

    private boolean intersects(LineSegment otherSegment) {
        double t = (direction.getX() * (otherSegment.startPos.getY() - startPos.getY())
                + direction.getY() * (startPos.getX() - otherSegment.startPos.getX()))
                / (otherSegment.direction.getX() * direction.getY() - otherSegment.direction.getY() * direction.getX());
        double u = (otherSegment.startPos.getX() + otherSegment.direction.getX() * t - startPos.getX())
                / direction.getX();
        return u >= 0 && u < 1 && t > 0 && t < 1;
    }

    public boolean intersects(List<LineSegment> lines) {
        return FunctionUtility.any(this::intersects, lines);
    }

    @Override
    public String toString() {
        return "LS{" +
                "startPos=" + startPos +
                ", direction=" + direction +
                '}';
    }
}