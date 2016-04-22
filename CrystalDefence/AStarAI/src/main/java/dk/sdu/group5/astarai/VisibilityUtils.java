package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dk.sdu.group5.astarai.FU.flatMap;
import static dk.sdu.group5.astarai.FU.lift2;

class VisibilityUtils {
    static List<LineSegment> getVisibilityLines(Vec startPos, List<Entity> barriers, Vec goalPos, float size) {
        List<Vec> points = new ArrayList<>();
        points.add(startPos);
        points.add(goalPos);
        points.addAll(flatMap(e -> getEntityCornerPoints(e, size), barriers));
        List<LineSegment> rays = lift2(LineSegment::new).apply(points, points); // rays
        List<LineSegment> barrierLines = flatMap(e -> getLines(e, size), barriers);
        return rays.stream().filter(a -> !a.intersects(barrierLines)).collect(Collectors.toList());
    }

    // TODO: 12/04/16 Describe what 0.1 and 0.2 does. Offsets?
    private static List<LineSegment> getLines(Entity entity, float size) {
        List<LineSegment> lines = new ArrayList<>(4);
        float a = 0.2f;
        float b = 0.1f;
        Vec topRightPoint = new Vec(maxX(entity, size) + 0.1, maxY(entity, size));
        Vec bottomRightPoint = new Vec(minX(entity, size) + 0.1, maxY(entity, size));
        Vec bottomLeftPoint = new Vec(minX(entity, size), minY(entity, size));
        Vec topLeftPoint = new Vec(maxX(entity, size), minY(entity, size));
        lines.add(new LineSegment(topRightPoint.plus(-a, -b), topLeftPoint.plus(-a, b)));
        lines.add(new LineSegment(topLeftPoint.plus(-b, a), bottomLeftPoint.plus(b, a)));
        lines.add(new LineSegment(bottomLeftPoint.plus(a, b), bottomRightPoint.plus(a, -b)));
        lines.add(new LineSegment(bottomRightPoint.plus(b, -a), topRightPoint.plus(-b, -a)));
        return lines;
    }

    // TODO: 12/04/16 Describe what 0.1 does
    private static List<Vec> getEntityCornerPoints(Entity entity, float size) {
        List<Vec> points = new ArrayList<>(4);
        points.add(new Vec(maxX(entity, size) + 0.1, maxY(entity, size)));
        points.add(new Vec(minX(entity, size) + 0.1, maxY(entity, size)));
        points.add(new Vec(minX(entity, size), minY(entity, size)));
        points.add(new Vec(maxX(entity, size), minY(entity, size)));
        return points;
    }

    private static float maxY(Entity entity, float size) {
        return entity.getBounds().getOriginY() + entity.getBounds().getWidth() + entity.getY() + size / 2;
    }

    private static float minY(Entity entity, float size) {
        return entity.getBounds().getOriginY() + entity.getY() - size / 2;
    }

    private static float minX(Entity entity, float size) {
        return entity.getBounds().getOriginX() + entity.getX() + size / 2;
    }

    private static float maxX(Entity entity, float size) {
        return entity.getBounds().getOriginX() + entity.getBounds().getWidth() + entity.getX() - size / 2;
    }
}
