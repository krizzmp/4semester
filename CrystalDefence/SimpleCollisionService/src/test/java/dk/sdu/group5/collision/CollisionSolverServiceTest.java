package dk.sdu.group5.collision;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollisionSolverServiceTest {

    final float tolerance = Math.ulp(1f);

    public CollisionSolverServiceTest() {
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

    /**
     * Test of solve method, of class CollisionSolverService.
     */
    @Test(expected = NullPointerException.class)
    public void testSolveNull() {

        Entity e1 = null;
        Entity e2 = null;

        CollisionSolverService instance = new CollisionSolverService();
        instance.solve(e1, e2);
    }

    @Test
    public void testSolverNoCollider() {

        Entity e1 = new Entity();
        Entity e2 = new Entity();

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e1.getX();
        float y1 = e1.getY();

        instance.solve(e1, e2);

        float x2 = e1.getX();
        float y2 = e1.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);
    }

    @Test
    public void testSolverStaticE1() {

        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("static");
        e1.addProperty("collidable");
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("collidable");

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e1.getX();
        float y1 = e1.getY();

        instance.solve(e1, e2);

        float x2 = e1.getX();
        float y2 = e1.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);

    }

    @Test
    public void testSolverStaticE2() {

        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("collidable");
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("static");
        e2.addProperty("collidable");

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e2.getX();
        float y1 = e2.getY();

        instance.solve(e1, e2);

        float x2 = e2.getX();
        float y2 = e2.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);
    }

    @Test
    public void testSolverDynamicE1() {

        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("collidable");
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("collidable");

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e1.getX();
        float y1 = e1.getY();

        instance.solve(e1, e2);

        float x2 = e1.getX();
        float y2 = e1.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);
    }

    @Test
    public void testSolverDynamicE2() {

        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("collidable");
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("collidable");

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e2.getX();
        float y1 = e2.getY();

        instance.solve(e1, e2);

        float x2 = e2.getX();
        float y2 = e2.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);
    }

    @Test
    public void testSolverNoOverlapRight() {

        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("collidable");
        e1.setX(2);
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("collidable");

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e2.getX();
        float y1 = e2.getY();

        instance.solve(e1, e2);

        float x2 = e2.getX();
        float y2 = e2.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);
    }

    @Test
    public void testSolverNoOverlapLeft() {

        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("collidable");
        e1.setX(-2);
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("collidable");

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e2.getX();
        float y1 = e2.getY();

        instance.solve(e1, e2);

        float x2 = e2.getX();
        float y2 = e2.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);
    }

    @Test
    public void testSolverNoOverlapUp() {

        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("collidable");
        e1.setY(2);
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("collidable");

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e2.getX();
        float y1 = e2.getY();

        instance.solve(e1, e2);

        float x2 = e2.getX();
        float y2 = e2.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);
    }

    @Test
    public void testSolverNoOverlapDown() {

        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e1.addProperty("collidable");
        e1.setY(-2);
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        e2.addProperty("collidable");

        CollisionSolverService instance = new CollisionSolverService();

        float x1 = e2.getX();
        float y1 = e2.getY();

        instance.solve(e1, e2);

        float x2 = e2.getX();
        float y2 = e2.getY();

        Assert.assertTrue(Math.abs(x2 - x1) < tolerance && Math.abs(y2 - y1) < tolerance);
    }

}
