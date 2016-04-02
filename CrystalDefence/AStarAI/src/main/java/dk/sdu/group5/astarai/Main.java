package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.AABB;
import dk.sdu.group5.common.data.BoxCollider;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        t();
    }

    static void t() {
        List<Entity> es = new ArrayList<>();
        es.add(createBarrier(200, 200, 64, 64));
        es.add(createBarrier(300, 100, 64, 64));
        es.add(createBarrier(100, 300, 64, 64));
        Vec start = new Vec(100, 100);
        Vec goal = new Vec(300, 300);
        LinkedList<Vec> node = PathFinder.getPath(start, es, goal);
        System.out.println(node);
//        System.out.println(getDirection(rc(node)));

    }

    static Entity createBarrier(float x, float y, float w, float h) {
        Entity e = new Entity(x, y, new BoxCollider(false, new AABB(x - w / 2, y - h / 2, x + w / 2, y + h / 2)), EntityType.ENEMY);
        return e;
    }
}
