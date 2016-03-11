package dk.sdu.group5.common.data;

import com.sun.javafx.geom.Vec2d;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SpawnController {
    private float timeSinceLastSpawn = 0;
    private List<Spawner> spawners = new LinkedList<>();
    private Random random = new Random();
    private static SpawnController instance;

    public static SpawnController getInstance() {
        if (instance == null)
            instance = new SpawnController();
        return instance;
    }

    public void update(World world, float delta) { // FIXME: 10/03/16 call this from render method in core
        timeSinceLastSpawn += delta;
        if (timeSinceLastSpawn > world.difficulty.spawnRate) {
            int b = world.difficulty.maxConcurrentDifficulty - world.difficulty.currentDifficulty;
            if (b > 0) {
                Entity entity = getSpawnerLessDifficultThan(b).spawn();
                setEntityPos(getPos(), entity);
                world.entities.add(entity);
                timeSinceLastSpawn = 0;
            }
        }
    }

    private Pos2d getPos() {
        return new Pos2d(20, 20);// FIXME: 11/03/16 return random point along the map egde
    }

    private void setEntityPos(Pos2d pos, Entity entity) {
        entity.setX(pos.x);
        entity.setY(pos.y);
    }

    private Spawner getSpawnerLessDifficultThan(int difficulty) {
        return chooseOne(spawners.stream().filter(spawner -> spawner.difficulty <= difficulty).collect(Collectors.toList()));
    }

    private <T> T chooseOne(List<T> spawners) {
        int i = random.nextInt(spawners.size());
        return spawners.get(i);
    }

    public void register(Spawner spawner) {
        spawners.add(spawner);
    }

    public void unRegister(Spawner spawner) {
        spawners.remove(spawner);
    }

    private class Pos2d {
        final int x;
        final int y;

        Pos2d(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
