package dk.sdu.group5.map;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.Posf2d;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ServiceProvider(service = IGameProcess.class)
public class MapGameProcess implements IGameProcess {
    private List<Entity> environment;
    private int MAX_AMOUNT = 10;

    @Override
    public void install() {
    }

    @Override
    public void start(World world) {
        environment = new ArrayList<>();

        Random rng = new Random();

        int columnCount = 800 / 32;
        int rowCount = 400 / 32;
        float offsetWidth = 800f / 32f;
        float offsetHeight = 400f / 32f;

        boolean[][] usedTiles = new boolean[columnCount][rowCount];

        int currentAmount = 0;
        while (currentAmount < MAX_AMOUNT) {
            Posf2d pos = getNextPosition(rng, usedTiles, columnCount, rowCount);
            environment.add(createEnvironmentEntity(pos.getX() * 32 + offsetWidth, pos.getY() * 32 + offsetHeight));
            currentAmount++;
        }
//        for (int i = 0; i < columnCount; i++) {
//            for (int j = 0; j < rowCount; j++) {
//                if(i >= columnCount/2 - 1 && i <= columnCount/2 + 1
//                        && j >= rowCount/2 - 1 && j <= rowCount/2 + 1){
//                    continue;
//                }
//
//                if(rng.nextFloat() >= 0.95){
//                    environment.add(createEnvironmentEntity(i * 32 + offsetWidth, j * 32 + offsetHeight));
//                    currentAmount++;
//                }
//
//                if(currentAmount == MAX_AMOUNT){
//                    break;
//                }
//            }
//            if(currentAmount == MAX_AMOUNT){
//                break;
//            }
//        }

        for (int i = 0; i < environment.size(); i++) {
            world.addEntity(environment.get(i));
        }
    }

    private Posf2d getNextPosition(Random rng, boolean[][] usedTiles, int columnCount, int rowCount) {
        int candidateX = -1, candidateY = -1, attempts = 0;
        do {
            candidateX = rng.nextInt(columnCount);
            candidateY = rng.nextInt(rowCount);
            attempts++;
        } while (usedTiles[candidateX][candidateY] || attempts != 100);

        if (candidateX == -1 || candidateY == -1) {
            return null;
        }

        return new Posf2d(candidateX, candidateY);
    }

    private Entity createEnvironmentEntity(float x, float y) {
        Entity entity = new Entity();
        entity.setType(EntityType.ENVIRONMENT);
        entity.setCollider(new SquareCollider(false, new AABB(-16, -16, 32, 32)));
        entity.setTexture("gridPattern.png");
        entity.addProperty("tangible");
        entity.addProperty("collidable");
        entity.addProperty("static");
        // TODO: 26/04/16 Get actual width and height.
        entity.setX(x);
        entity.setY(y);
        return entity;
    }

    @Override
    public void update(World world, float delta) {
    }

    @Override
    public void stop(World world) {
    }

    @Override
    public void uninstall() {

    }

}
