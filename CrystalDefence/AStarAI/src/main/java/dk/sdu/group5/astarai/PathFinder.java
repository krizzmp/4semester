package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;

import java.util.LinkedList;
import java.util.List;

import static dk.sdu.group5.astarai.AStar.aStar;

public class PathFinder {

    private static Node generateSolution(Vec startPos, List<Entity> barriers, Vec goalPos, float size) {
        return aStar(startPos, goalPos, VisibilityUtils.getVisibilityLines(startPos, barriers, goalPos, size));
    }

    private static LinkedList<Vec> getPath(Vec startPos, List<Entity> barriers, Vec goalPos, float size) {
        Node node = generateSolution(startPos, barriers, goalPos, size);
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

    // TODO: 12/04/16 Maybe a better name for size? Perhaps offset or extend?
    private static Vec getDirection(Vec startPos, List<Entity> barriers, Vec goalPos, float size) {
        return getDirection(getPath(startPos, barriers, goalPos, size));
    }

    static Vec getDirection(Entity srcEntity, List<Entity> barriers, Entity targetEntity) {
        return getDirection(getEntityPosition(srcEntity), barriers, getEntityPosition(targetEntity),
                srcEntity.getCollider().getBounds().getMaxX() * 2);
    }

    private static Vec getEntityPosition(Entity entity) {
        return new Vec(entity.getX(), entity.getY());
    }
}
