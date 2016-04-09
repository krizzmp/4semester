package dk.sdu.group5.astarai;


import java.util.List;

public class LineSegment {
    Vec p;
    Vec d;

    public LineSegment(Vec from, Vec to) {
        this.p = from;
        this.d = to.minus(from);
    }

    public boolean intersects(LineSegment line) {
        LineSegment r = this;
        LineSegment s = line;
        double t2 = (r.d.x * (s.p.y - r.p.y) + r.d.y * (r.p.x - s.p.x)) / (s.d.x * r.d.y - s.d.y * r.d.x);
        double t1 = (s.p.x + s.d.x * t2 - r.p.x) / r.d.x;
        return t1 >= 0 && t1 < 1 && t2 > 0 && t2 < 1;
    }

    public boolean intersects(List<LineSegment> lines) {
        return FU.any(this::intersects, lines);
    }

    @Override
    public String toString() {
        return "LS{" +
                "p=" + p +
                ", d=" + d +
                '}';
    }
}