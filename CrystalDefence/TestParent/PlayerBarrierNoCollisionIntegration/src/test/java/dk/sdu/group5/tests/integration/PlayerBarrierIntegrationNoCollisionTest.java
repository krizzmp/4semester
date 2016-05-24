package dk.sdu.group5.tests.integration;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.KeyState;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.ICollisionDetectorService;
import dk.sdu.group5.common.services.IGameProcess;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

public class PlayerBarrierIntegrationNoCollisionTest {
    private Collection<? extends IGameProcess> processes;
    private World world;
    private float delta;

    public PlayerBarrierIntegrationNoCollisionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {        
        processes = Lookup.getDefault().lookupAll(IGameProcess.class);
        world = new World(null);
        world.setGameKeys(new GameKeys());

        // Time per frame if running at 60 fps
        delta = 1f / 60f;
    }

    @After
    public void tearDown() {
        processes.forEach(p -> p.stop(world));
    }

    @Test
    public void testBarrierCreatedUp() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementUp().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();

        Assert.assertNotNull(barrier);
    }

    @Test
    public void testBarrierCreatedAbovePlayer() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementUp().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();

        Assert.assertTrue(barrier.getY() > player.getY());
    }

    @Test
    public void testBarrierCreatedDown() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementDown().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();

        Assert.assertNotNull(barrier);
    }

    @Test
    public void testBarrierCreatedBelowPlayer() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementDown().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();

        Assert.assertTrue(barrier.getY() < player.getY());
    }

    @Test
    public void testBarrierCreatedRight() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementRight().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();

        Assert.assertNotNull(barrier);
    }

    @Test
    public void testBarrierCreatedRightSidePlayer() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementRight().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();

        Assert.assertTrue(barrier.getX() > player.getX());
    }

    @Test
    public void testBarrierCreatedLeft() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementLeft().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();

        Assert.assertNotNull(barrier);
    }

    @Test
    public void testBarrierCreatedLeftSidePlayer() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementLeft().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();

        Assert.assertTrue(barrier.getX() < player.getX());
    }

    @Test
    public void testBarrierCreatedAboveMovedPlayer() {
        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementUp().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        player.setY(player.getY() + 100);

        processes.forEach(process -> process.update(world, delta));
        
        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();
        
        Assert.assertTrue(barrier.getY() > player.getY());
    }

    @ServiceProvider(service = ICollisionDetectorService.class)
    public static class StubCollisionNegativeDetector implements ICollisionDetectorService{
        @Override
        public boolean collides(Entity ent1, Entity ent2) {
            return false;
        }
    }
}
