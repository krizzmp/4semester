package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.AABB;
import dk.sdu.group5.common.data.BoxCollider;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static dk.sdu.group5.astarai.AStar.aStar;

public class PathFinder {
    static Vec getDirection(Vec start, List<Entity> barriers, Vec goal){
        return getDirection(getPath(q(start, barriers, goal)));
    }
    static Node q(Vec start, List<Entity> barriers, Vec goal){
        Node node = aStar(start, goal, VisibilityUtils.getVisibilityLines(start,barriers,goal));
        return node;
    }
    static LinkedList<Vec> getPath(Vec start, List<Entity> barriers, Vec goal){
        Node node = aStar(start, goal, VisibilityUtils.getVisibilityLines(start,barriers,goal));
        return getPath(node);
    }
    static LinkedList<Vec> getPath(Node node){
        LinkedList<Vec> vecs = new LinkedList<>();
        Node current = node;
        while(true){
            vecs.addFirst(current.item);
            if(current.parent == null){
                break;
            }
            current = current.parent;
        }
        return vecs;
    }
    static Vec getDirection(List<Vec> vs){
        return vs.get(1).minus(vs.get(0));
    }
}
