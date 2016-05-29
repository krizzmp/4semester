package dk.sdu.group5.astarai;

import static dk.sdu.group5.astarai.AStar.aStar;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.collision.AABB;
import java.util.LinkedList;
import java.util.List;

class PathFinder {

    private static Node generateSolution(Vec startPos, List<Entity> barriers, Vec goalPos, AABB srcBounds) {
        return aStar(startPos, goalPos, VisibilityUtils.getVisibilityLines(startPos, barriers, goalPos, srcBounds));
    }

    private static LinkedList<Vec> getPath(Vec startPos, List<Entity> barriers, Vec goalPos, AABB srcBounds) {
        Node node = generateSolution(startPos, barriers, goalPos, srcBounds);
        return getPath(node);
    }

    private static LinkedList<Vec> getPath(Node node) {
        LinkedList<Vec> points = new LinkedList<>();
        Node currentNode = node;
        while (node != null) {
            points.addFirst(currentNode.getItem());
            if (currentNode.getParent() == null) {
                break;
            }
            currentNode = currentNode.getParent();
        }
        return points;
    }

    private static Vec getDirection(List<Vec> points) {
        if (points.size() < 2) {
            return new Vec(0.001, 0.1);
        }
        return points.get(1).minus(points.get(0));
    }

    private static Vec getDirection(Vec startPos, List<Entity> barriers, Vec goalPos, AABB srcBounds) {
        return getDirection(getPath(startPos, barriers, goalPos, srcBounds));
    }

    static Vec getDirection(Entity srcEntity, List<Entity> barriers, Entity targetEntity) {
        return getDirection(getEntityPosition(srcEntity), barriers,
                getEntityPosition(targetEntity), srcEntity.getBounds());
    }

    private static Vec getEntityPosition(Entity entity) {
        return new Vec(entity.getX(), entity.getY());
    }
}
