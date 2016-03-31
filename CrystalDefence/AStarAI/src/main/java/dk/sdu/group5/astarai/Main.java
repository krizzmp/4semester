package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.AABB;
import dk.sdu.group5.common.data.BoxCollider;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        LineSegment ray = new LineSegment(new Vec(10, 10), new Vec(40, 40));
        LineSegment seg = new LineSegment(new Vec(10, 30), new Vec(40, 20));
        boolean intersects = ray.intersects(seg);
        System.out.println(intersects);

        List<Entity> es = new ArrayList<>();
        List<Vec> ps = new ArrayList<>();
        es.add(createBarrier(200, 200, 64, 64));
        es.add(createBarrier(300,100,64,64));
//        es.add(createBarrier(100,300,64,64));
        ps.add(new Vec(100, 100));
        ps.addAll(FU.flatMap(Main::getPoints, es));
        ps.add(new Vec(300, 300));
        System.out.println(ps);
        List<LineSegment> rays = FU.lift2(LineSegment::new).apply(ps, ps); // rays
        List<LineSegment> segments = FU.flatMap(Main::getLines, es); // segments
        System.out.println(rays);
        System.out.println(segments);
        Stream<LineSegment> lineSegmentStream = rays.stream().filter(a -> !a.intersects(segments));
        System.out.println(lineSegmentStream.collect(Collectors.toList()));
    }

    static Entity createBarrier(float x, float y, float w, float h) {
        Entity e = new Entity(x, y, new BoxCollider(false, new AABB(x - w / 2, y - h / 2, x + w / 2, y + h / 2)), EntityType.ENEMY);
        return e;
    }

    static List<LineSegment> getLines(Entity e) {
        List<LineSegment> lines = new ArrayList<>(4);
        float a = 0.2f;
        float b = 0.1f;
        Vec vec11 = new Vec(maxX(e), maxY(e));
        Vec vec01 = new Vec(minX(e), maxY(e));
        Vec vec00 = new Vec(minX(e), minY(e));
        Vec vec10 = new Vec(maxX(e), minY(e));
        lines.add(new LineSegment(vec11.plus(-a, -b), vec10.plus(-a, b)));
        lines.add(new LineSegment(vec10.plus(-b, a), vec00.plus(b, a)));
        lines.add(new LineSegment(vec00.plus(a, b), vec01.plus(a, -b)));
        lines.add(new LineSegment(vec01.plus(b, -a), vec11.plus(-b, -a)));
        return lines;
    }

    static List<Vec> getPoints(Entity e) {
        List<Vec> points = new ArrayList<>(4);
        points.add(new Vec(maxX(e), maxY(e)));
        points.add(new Vec(minX(e), maxY(e)));
        points.add(new Vec(minX(e), minY(e)));
        points.add(new Vec(maxX(e), minY(e)));
        return points;
    }

    private static float maxY(Entity e) {
        return e.getCollider().getAABB().getMaxY();
    }

    private static float minY(Entity e) {
        return e.getCollider().getAABB().getMinY();
    }

    private static float minX(Entity e) {
        return e.getCollider().getAABB().getMinX();
    }

    private static float maxX(Entity e) {
        return e.getCollider().getAABB().getMaxX();
    }
}
