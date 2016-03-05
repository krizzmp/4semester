package dk.sdu.group5.common.data;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SpawnController {
    private float timeSinceLastSpawn=0;
    private List<Spawner> spawners;
    private Random random = new Random();
    void update(World world,float delta){
        timeSinceLastSpawn+=delta;
        if(timeSinceLastSpawn>world.difficulty.spawnRate){
            int b =  world.difficulty.maxConcurrentDifficulty - world.difficulty.currentDifficulty;
            if(b>0){
                world.entities.add(getEnemyLessDifficultThan(b, world).spawn());
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
}
