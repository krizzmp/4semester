package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;

import java.util.LinkedList;
import java.util.List;

import static dk.sdu.group5.astarai.AStar.aStar;

public class PathFinder {

    static Node q(Vec start, List<Entity> barriers, Vec goal) {
        Node node = aStar(start, goal, VisibilityUtils.getVisibilityLines(start, barriers, goal));
        return node;
    }

    static LinkedList<Vec> getPath(Vec start, List<Entity> barriers, Vec goal) {
        Node node = q(start, barriers, goal);
        return getPath(node);
    }

    static LinkedList<Vec> getPath(Node node) {
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

    static Vec getDirection(List<Vec> vs) {
        if(vs.size()<2){
            System.out.println(vs);
            return new Vec(0.001,0.1);
        }
        return vs.get(1).minus(vs.get(0));
    }

    static Vec getDirection(Vec start, List<Entity> barriers, Vec goal) {
        return getDirection(getPath(start, barriers, goal));
    }

    static Vec getDirection(Entity e, List<Entity> barriers, Entity t) {
        return getDirection(vec(e), barriers, vec(t));
    }

    private static Vec vec(Entity e) {
        return new Vec(e.getX(), e.getY());
    }
}
