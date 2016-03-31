package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.AABB;
import dk.sdu.group5.common.data.BoxCollider;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LineSegment ray = new LineSegment(new Vec(10, 10), new Vec(40, 40));
        LineSegment seg = new LineSegment(new Vec(10, 30), new Vec(40, 20));
        boolean intersects = ray.intersects(seg);
        System.out.println(intersects);

        List<Entity> es = new ArrayList<>();
        List<Vec> ps = new ArrayList<>();
        es.add(createBarrier(200,200,64,64));
//        es.add(createBarrier(300,100,64,64));
//        es.add(createBarrier(100,300,64,64));
        ps.add(new Vec(100,100));
        ps.addAll(FU.flatMap(Main::getPoints,es));
        ps.add(new Vec(300,300));
        System.out.println(ps);
        List<LineSegment> lineSegments = FU.lift2(LineSegment::new).apply(ps, ps); // rays
        System.out.println(lineSegments);
    }

    static Entity createBarrier(float x, float y, float w, float h) {
        Entity e = new Entity(x, y, new BoxCollider(false, new AABB(x - w / 2, y - h / 2, x + w / 2, y + h / 2)), EntityType.ENEMY);
        return e;
    }
    static List<Vec> getPoints(Entity e){
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
