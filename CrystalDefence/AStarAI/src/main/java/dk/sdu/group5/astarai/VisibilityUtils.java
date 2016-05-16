package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.collision.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dk.sdu.group5.astarai.FU.flatMap;
import static dk.sdu.group5.astarai.FU.lift2;

class VisibilityUtils {
    static List<LineSegment> getVisibilityLines(Vec startPos, List<Entity> barriers, Vec goalPos, AABB srcBounds) {
        List<Vec> points = new ArrayList<>();
        points.add(startPos);
        points.add(goalPos);
        points.addAll(flatMap(e -> getEntityCornerPoints(e, srcBounds), barriers));
        List<LineSegment> rays = lift2(LineSegment::new).apply(points, points); // rays
        List<LineSegment> barrierLines = flatMap(e -> getLines(e, srcBounds), barriers);
        return rays.stream().filter(a -> !a.intersects(barrierLines)).collect(Collectors.toList());
    }

    // TODO: 12/04/16 Describe what 0.1 and 0.2 does. Offsets?
    private static List<LineSegment> getLines(Entity entity, AABB srcBounds) {
        List<LineSegment> lines = new ArrayList<>(4);
        float a = 0.2f;
        float b = 0.1f;
        Vec topRightPoint = new Vec(maxX(entity, srcBounds) + 0.1, maxY(entity, srcBounds));
        Vec bottomRightPoint = new Vec(minX(entity, srcBounds) + 0.1, maxY(entity, srcBounds));
        Vec bottomLeftPoint = new Vec(minX(entity, srcBounds), minY(entity, srcBounds));
        Vec topLeftPoint = new Vec(maxX(entity, srcBounds), minY(entity, srcBounds));
        lines.add(new LineSegment(topRightPoint.plus(-a, -b), topLeftPoint.plus(-a, b)));
        lines.add(new LineSegment(topLeftPoint.plus(-b, a), bottomLeftPoint.plus(b, a)));
        lines.add(new LineSegment(bottomLeftPoint.plus(a, b), bottomRightPoint.plus(a, -b)));
        lines.add(new LineSegment(bottomRightPoint.plus(b, -a), topRightPoint.plus(-b, -a)));
        return lines;
    }

    // TODO: 12/04/16 Describe what 0.1 does
    private static List<Vec> getEntityCornerPoints(Entity entity, AABB srcBounds) {
        List<Vec> points = new ArrayList<>(4);
        points.add(new Vec(maxX(entity, srcBounds) + 0.1, maxY(entity, srcBounds)));
        points.add(new Vec(minX(entity, srcBounds) + 0.1, maxY(entity, srcBounds)));
        points.add(new Vec(minX(entity, srcBounds), minY(entity, srcBounds)));
        points.add(new Vec(maxX(entity, srcBounds), minY(entity, srcBounds)));
        return points;
    }

    private static float maxY(Entity entity, AABB srcBounds) {
        return entity.getBounds().getOriginY() + entity.getBounds().getHeight()
                + entity.getY() + srcBounds.getHeight() / 2f;
    }

    private static float minY(Entity entity, AABB srcBounds) {
        return entity.getBounds().getOriginY() + entity.getY() - srcBounds.getHeight() / 2f;
    }

    private static float minX(Entity entity, AABB srcBounds) {
        return entity.getBounds().getOriginX() + entity.getX() - srcBounds.getWidth() / 2f;
    }

    private static float maxX(Entity entity, AABB srcBounds) {
        return entity.getBounds().getOriginX() + entity.getBounds().getWidth()
                + entity.getX() + srcBounds.getWidth() / 2f;
    }
}
