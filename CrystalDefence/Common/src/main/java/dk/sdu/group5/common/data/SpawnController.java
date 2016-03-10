package dk.sdu.group5.common.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SpawnController {
    private float timeSinceLastSpawn=0;
    private List<Spawner> spawners = new LinkedList<>();
    private Random random = new Random();
    private static SpawnController instance;

    public static SpawnController getInstance() {
        if(instance == null)
            instance = new SpawnController();
        return instance;
    }

    public void update(World world,float delta){ // FIXME: 10/03/16 call this from render method in core
        timeSinceLastSpawn+=delta;
        if(timeSinceLastSpawn>world.difficulty.spawnRate){
            int b =  world.difficulty.maxConcurrentDifficulty - world.difficulty.currentDifficulty;
            if(b>0){
                world.entities.add(getEnemyLessDifficultThan(b, world).spawn());// FIXME: 10/03/16 needs to set x and y coords of the entity
                timeSinceLastSpawn = 0;
            }
        }
    }

    private Spawner getEnemyLessDifficultThan(int difficulty, World world) {
        return chooseOne(spawners.stream().filter(spawner->spawner.difficulty<=difficulty).collect(Collectors.toList()));
    }

    private <T> T chooseOne(List<T> spawners) {
        int i = random.nextInt(spawners.size());
        return spawners.get(i);
    }
    public void register(Spawner spawner){
        spawners.add(spawner);
    }

    public void unRegister(Spawner spawner) {
        spawners.remove(spawner);
    }
}
