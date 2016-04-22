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

    // TODO: 12/04/16 Describe what t1 and t2 is.
    private boolean intersects(LineSegment otherSegment) {
        double t2 = (direction.getX() * (otherSegment.startPos.getY() - startPos.getY())
                + direction.getY() * (startPos.getX() - otherSegment.startPos.getX()))
                / (otherSegment.direction.getX() * direction.getY() - otherSegment.direction.getY() * direction.getX());
        double t1 = (otherSegment.startPos.getX() + otherSegment.direction.getX() * t2 - startPos.getX())
                / direction.getX();
        return t1 >= 0 && t1 < 1 && t2 > 0 && t2 < 1;
    }

    public boolean intersects(List<LineSegment> lines) {
        return FU.any(this::intersects, lines);
    }

    @Override
    public String toString() {
        return "LS{" +
                "startPos=" + startPos +
                ", direction=" + direction +
                '}';
    }
}