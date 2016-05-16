package dk.sdu.group5.spawner;

import dk.sdu.group5.common.data.*;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ServiceProvider(service = IGameProcess.class)
public class SpawnerGameProcess implements IGameProcess
{
    private final Random random = new Random();
    private float timeSinceLastSpawn;
    
    @Override
    public void start(World world)
    {
    }

    @Override
    public void update(World world, float delta)
    {
        timeSinceLastSpawn += delta;
        ifShouldSpawn(world.getDifficulty(), b -> {
            Optional<SpawnData> spawnDataToSpawn = getSpawner(world.getSpawnData(),b);
            spawnDataToSpawn.ifPresent(spawnData -> {
                Entity entity = copyEntity(spawnData.getPrototypeEntity());
                setEntityPos(getPosOnEdge(800, 400), entity);
                Difficulty difficulty = world.getDifficulty();
                difficulty.setCurrentDifficulty(difficulty.getCurrentDifficulty() 
                        + spawnData.getDifficulty());
                spawnData.getSpawnedEntities().add(entity);
                world.addEntity(entity);
            });
            reset();
        });
    }

    @Override
    public void stop(World world)
    {
    }
    
    private void reset() {
        timeSinceLastSpawn = 0;
    }

    private void ifShouldSpawn(Difficulty difficulty, Consumer<Integer> consumer) {
        if (timeSinceLastSpawn > difficulty.getSpawnRate()) {
            int difficultyRemaining = difficulty.getMaxConcurrentDifficulty() - difficulty.getCurrentDifficulty();
            if (difficultyRemaining > 0) {
                consumer.accept(difficultyRemaining);
            }
        }
    }

    private Posf2d getPosOnEdge(int width, int height) {
        int rnd = rnd(4);
        Posf2d pos = new Posf2d(0, 0);
        switch (rnd) {
            case 0:
                pos = new Posf2d(rnd(width), 0);
                break;
            case 1:
                pos = new Posf2d(rnd(width), height);
                break;
            case 2:
                pos = new Posf2d(0, rnd(height));
                break;
            case 3:
                pos = new Posf2d(width, rnd(height));
                break;
        }

        return pos;
    }

    private int rnd(int i) {
        return random.nextInt(i);
    }

    private void setEntityPos(Posf2d pos, Entity entity) {
        entity.setX(pos.getX());
        entity.setY(pos.getY());
    }

    private Optional<SpawnData> getSpawner(Collection<SpawnData> spawnData, int maxDifficulty) {
        Predicate<SpawnData> validDifficulty = spawnDataToCheck -> spawnDataToCheck.getDifficulty() <= maxDifficulty;
        List<SpawnData> filteredSpawnData = spawnData.stream().filter(validDifficulty).collect(Collectors.toList());
        return chooseOne(filteredSpawnData);
    }

    private <T> Optional<T> chooseOne(List<T> spawners) {
        int size = spawners.size();
        if (size > 0) {
            return Optional.of(spawners.get(rnd(size)));
        } else {
            return Optional.empty();
        }
    }
    
    private Entity copyEntity(Entity entity){
        
        Entity copyEntity = new Entity();
        copyEntity.setType(entity.getType());
        copyEntity.setHealth(entity.getHealth());
        copyEntity.setSpeed(entity.getSpeed());
        copyEntity.setTexturePath(entity.getTexturePath());
        
        // Colliders are immutable like Strings, making it possible to reuse 
        // them for multiple entities.
        copyEntity.setCollider(entity.getCollider());
        
        entity.getProperties().forEach(property -> copyEntity.addProperty(property));
        return copyEntity;
    }
}
