package dk.sdu.group5.tests.integration;

import dk.sdu.group5.common.data.*;
import dk.sdu.group5.common.services.IGameProcess;
import org.junit.*;
import org.openide.util.Lookup;

import java.util.Collection;

public class PlayerWeaponIntegrationTest {
    private Collection<? extends IGameProcess> processes;
    private World world;
    private float delta;

    public PlayerWeaponIntegrationTest() {
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
    }

    @Test
    public void testShootUpBulletCreated() {
        world.getGameKeys().getPlayerShootUp().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertNotNull(bullet);
    }

    @Test
    public void testShootUpBulletCreatedAbovePlayer() {
        world.getGameKeys().getPlayerShootUp().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getY() > player.getY());
    }

    @Test
    public void testShootDownBulletCreated() {
        world.getGameKeys().getPlayerShootDown().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertNotNull(bullet);
    }

    @Test
    public void testShootDownBulletCreatedBelowPlayer() {
        world.getGameKeys().getPlayerShootDown().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getY() < player.getY());
    }

    @Test
    public void testShootRightBulletCreated() {
        world.getGameKeys().getPlayerShootRight().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertNotNull(bullet);
    }

    @Test
    public void testShootRightBulletCreatedRightSidePlayer() {
        world.getGameKeys().getPlayerShootRight().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getX() > player.getX());
    }

    @Test
    public void testShootLeftBulletCreated() {
        world.getGameKeys().getPlayerShootLeft().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertNotNull(bullet);
    }

    @Test
    public void testShootLeftBulletCreatedLeftSidePlayer() {
        world.getGameKeys().getPlayerShootLeft().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getX() < player.getX());
    }

    @Test
    public void testShootUpRightBulletCreated() {
        world.getGameKeys().getPlayerShootUp().setState(KeyState.HELD);
        world.getGameKeys().getPlayerShootRight().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertNotNull(bullet);
    }

    @Test
    public void testShootUpRightBulletCreatedUpRightToPlayer() {
        world.getGameKeys().getPlayerShootUp().setState(KeyState.HELD);
        world.getGameKeys().getPlayerShootRight().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getX() > player.getX()
                && bullet.getY() > player.getY());
    }

    @Test
    public void testShootUpLeftBulletCreated() {
        world.getGameKeys().getPlayerShootUp().setState(KeyState.HELD);
        world.getGameKeys().getPlayerShootLeft().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertNotNull(bullet);
    }

    @Test
    public void testShootUpLeftBulletCreatedUpLeftToPlayer() {
        world.getGameKeys().getPlayerShootUp().setState(KeyState.HELD);
        world.getGameKeys().getPlayerShootLeft().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getX() < player.getX() && bullet.getY() > player.getY());
    }

    @Test
    public void testShootDownRightBulletCreated() {
        world.getGameKeys().getPlayerShootDown().setState(KeyState.HELD);
        world.getGameKeys().getPlayerShootRight().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertNotNull(bullet);
    }

    @Test
    public void testShootDownRightBulletCreatedDownRightToPlayer() {
        world.getGameKeys().getPlayerShootDown().setState(KeyState.HELD);
        world.getGameKeys().getPlayerShootRight().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getX() > player.getX() && bullet.getY() < player.getY());
    }

    @Test
    public void testShootDownLeftBulletCreated() {
        world.getGameKeys().getPlayerShootDown().setState(KeyState.HELD);
        world.getGameKeys().getPlayerShootLeft().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertNotNull(bullet);
    }

    @Test
    public void testShootDownLeftBulletCreatedDownLeftToPlayer() {
        world.getGameKeys().getPlayerShootDown().setState(KeyState.HELD);
        world.getGameKeys().getPlayerShootLeft().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getX() < player.getX() && bullet.getY() < player.getY());
    }

    @Test
    public void testShootUpBulletCreatedAboveMovedPlayer() {
        world.getGameKeys().getPlayerShootUp().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));

        Entity player = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.PLAYER).findFirst().get();
        player.setY(player.getY() + 100);

        processes.forEach(process -> process.update(world, delta));

        Entity bullet = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BULLET).findFirst().get();

        Assert.assertTrue(bullet.getY() > player.getY());
    }
}
