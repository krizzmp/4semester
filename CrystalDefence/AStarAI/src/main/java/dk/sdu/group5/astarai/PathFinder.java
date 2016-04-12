package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;

import java.util.LinkedList;
import java.util.List;

import static dk.sdu.group5.astarai.AStar.aStar;

class PathFinder {

    private static Node q(Vec start, List<Entity> barriers, Vec goal, float size) {
        return aStar(start, goal, VisibilityUtils.getVisibilityLines(start, barriers, goal, size));
    }

    private static LinkedList<Vec> getPath(Vec start, List<Entity> barriers, Vec goal, float size) {
        Node node = q(start, barriers, goal, size);
        return getPath(node);
    }

    private static LinkedList<Vec> getPath(Node node) {
        LinkedList<Vec> vecs = new LinkedList<>();
        Node current = node;
        while (node != null) {
            vecs.addFirst(current.item);
            if (current.parent == null) {
                break;
            }
            current = current.parent;
        }
        return vecs;
    }

    private static Vec getDirection(List<Vec> vs) {
        if(vs.size()<2){
            System.out.println(vs);
            return new Vec(0.001,0.1);
        }
        return vs.get(1).minus(vs.get(0));
    }

    private static Vec getDirection(Vec start, List<Entity> barriers, Vec goal, float size) {
        return getDirection(getPath(start, barriers, goal, size));
    }

    static Vec getDirection(Entity e, List<Entity> barriers, Entity t) {
        return getDirection(vec(e), barriers, vec(t), e.getCollider().getBounds().getMaxX()*2);
    }

    private static Vec vec(Entity e) {
        return new Vec(e.getX(), e.getY());
    }
}
