package dk.sdu.group5.astarai;


import java.util.List;

class LineSegment {
    final Vec p;
    final Vec d;

    public LineSegment(Vec from, Vec to) {
        p = from;
        d = to.minus(from);
    }

    private boolean intersects(LineSegment line) {
        LineSegment r = this;
        double t2 = (r.d.x * (line.p.y - r.p.y) + r.d.y * (r.p.x - line.p.x)) / (line.d.x * r.d.y - line.d.y * r.d.x);
        double t1 = (line.p.x + line.d.x * t2 - r.p.x) / r.d.x;
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