package dk.sdu.group5.tests;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.ICollisionDetectorService;
import dk.sdu.group5.common.services.ICollisionSolverService;
import org.junit.*;
import org.openide.util.Lookup;

public class CollisionDetectionResolutionTest {

    public CollisionDetectionResolutionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDetectionAfterSolveNoCollision() {
        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("collidable");

        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("collidable");

        ICollisionDetectorService detector;
        ICollisionSolverService solver;

        detector = Lookup.getDefault().lookup(ICollisionDetectorService.class);
        solver = Lookup.getDefault().lookup(ICollisionSolverService.class);

        solver.solve(e1, e2);

        boolean expResult = false;
        boolean result = detector.collides(e1, e2);
        Assert.assertEquals(expResult, result);
    }
}
