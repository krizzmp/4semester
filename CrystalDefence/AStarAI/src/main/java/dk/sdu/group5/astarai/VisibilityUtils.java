package dk.sdu.group5.astarai;

import static dk.sdu.group5.astarai.FunctionUtility.flatMap;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.collision.AABB;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class VisibilityUtils {
    
    static List<LineSegment> getVisibilityLines(Vec startPos, List<Entity> obstacles, Vec goalPos, AABB srcBounds) {
        List<Vec> points = new ArrayList<>();
        points.add(startPos);
        points.add(goalPos);
        
        // Collect all obstacle points
        Collection<Vec> obstaclePoints = flatMap(e -> getEntityCornerPoints(e, srcBounds), obstacles);
        points.addAll(obstaclePoints);
        
        // Find all rays, i.e. the line segments between all combinations of points
        List<LineSegment> rays = getRays(points);
        
        // Find all rays segments not intersecting with obstacle lines
        List<LineSegment> obstacleLines = flatMap(e -> getLines(e, srcBounds), obstacles);
        return rays.stream().filter(a -> !a.intersects(obstacleLines)).collect(Collectors.toList());
    }

    private static List<LineSegment> getLines(Entity entity, AABB srcBounds) {  
        Vec topRightPoint = new Vec(maxX(entity, srcBounds), maxY(entity, srcBounds));
        Vec topLeftPoint = new Vec(minX(entity, srcBounds), maxY(entity, srcBounds));
        Vec bottomLeftPoint = new Vec(minX(entity, srcBounds), minY(entity, srcBounds));
        Vec bottomRightPoint = new Vec(maxX(entity, srcBounds), minY(entity, srcBounds));
        
        List<LineSegment> lines = new ArrayList<>(4);
        lines.add(new LineSegment(topRightPoint, bottomRightPoint));
        lines.add(new LineSegment(bottomRightPoint, bottomLeftPoint));
        lines.add(new LineSegment(bottomLeftPoint, topLeftPoint));
        lines.add(new LineSegment(topLeftPoint, topRightPoint));
        return lines;
    }

    private static List<Vec> getEntityCornerPoints(Entity entity, AABB srcBounds) {
        float offset = 0.1f;
        
        List<Vec> points = new ArrayList<>(4);
        points.add(new Vec(maxX(entity, srcBounds) + offset, maxY(entity, srcBounds) + offset));
        points.add(new Vec(minX(entity, srcBounds) - offset, maxY(entity, srcBounds) + offset));
        points.add(new Vec(minX(entity, srcBounds) - offset, minY(entity, srcBounds) - offset));
        points.add(new Vec(maxX(entity, srcBounds) + offset, minY(entity, srcBounds) - offset));
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
    
    public static List<LineSegment> getRays(List<Vec> points) {
        List<LineSegment> raySegments = new ArrayList<>();
        
        for (int i = 0; i < points.size(); i++) {
            for (int j = i+1; j < points.size(); j++) {
                raySegments.add(new LineSegment(points.get(i), points.get(j)));
            }
        }
            
        return raySegments;
    }
}
