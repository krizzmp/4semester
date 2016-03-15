package dk.sdu.group5.common.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SpawnController {
    private float timeSinceLastSpawn;
    private final List<Spawner> spawners = new LinkedList<>();
    private final Random random = new Random();
    private static SpawnController instance;

    public static SpawnController getInstance() {
        if (instance == null)
            instance = new SpawnController();
        return instance;
    }

    public void update(World world, float delta) {
        timeSinceLastSpawn += delta;
        ifShouldSpawn(world.difficulty, b -> {
            Spawner spawner = getSpawnerLessDifficultThan(b);
            if (spawner != null){
                Entity entity = spawner.spawn();
                setEntityPos(getPosOnEdge(800, 400), entity);
                world.entities.add(entity);
            }
            reset();
        });
    }

    private void reset() {
        timeSinceLastSpawn = 0;
    }

    private void ifShouldSpawn(Difficulty difficulty, Consumer<Integer> consumer) {
        if (timeSinceLastSpawn > difficulty.spawnRate) {
            int difficultyRemaining = difficulty.maxConcurrentDifficulty - difficulty.currentDifficulty;
            if (difficultyRemaining > 0) {
                consumer.accept(difficultyRemaining);
            }
        }
    }

    private Pos2d getPosOnEdge(int width, int height) {
        int rnd = rnd(4);
        Pos2d pos2d = new Pos2d(0, 0);
        switch (rnd) {
            case 0:
                pos2d = new Pos2d(rnd(width), 0);
                break;
            case 1:
                pos2d = new Pos2d(rnd(width), height);
                break;
            case 2:
                pos2d = new Pos2d(0, rnd(height));
                break;
            case 3:
                pos2d = new Pos2d(width, rnd(height));
                break;
        }

        return pos2d;
    }

    private int rnd(int i) {
        return random.nextInt(i);
    }

    private void setEntityPos(Pos2d pos, Entity entity) {
        entity.setX(pos.x);
        entity.setY(pos.y);
    }

    private Spawner getSpawnerLessDifficultThan(int difficulty) {
        return chooseOne(spawners.stream().filter(spawner -> spawner.getDifficulty() <= difficulty).collect(Collectors.toList()));
    }

    private <T> T chooseOne(List<T> spawners) {
        int size = spawners.size();
        if (size > 0) {
            int i = rnd(size);
            return spawners.get(i);
        } else {
            return null;
        }
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
