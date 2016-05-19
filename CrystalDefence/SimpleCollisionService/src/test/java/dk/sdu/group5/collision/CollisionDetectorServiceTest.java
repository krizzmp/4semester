package dk.sdu.group5.collision;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollisionDetectorServiceTest
{
    
    public CollisionDetectorServiceTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of collides method, of class CollisionDetectorService.
     */
    @Test(expected = NullPointerException.class)
    public void testCollidesNull()
    {
        System.out.println("Collides null");
        
        Entity e1 = null;
        Entity e2 = null;
        
        CollisionDetectorService instance = new CollisionDetectorService();
        instance.collides(e1, e2);
    }
    
    @Test
    public void testCollidesNoCollider()
    {
        System.out.println("Collides no collider");
        
        Entity e1 = new Entity();
        Entity e2 = new Entity();
        
        CollisionDetectorService instance = new CollisionDetectorService();
        
        boolean expResult = false;
        boolean result = instance.collides(e1, e2);
        Assert.assertEquals(expResult, result);
    }
    
    @Test
    public void testCollidesOverlappingNotTrigger()
    {
        System.out.println("Collides not collidable");
        
        Entity e1 = new Entity();
        e1.setCollider(new SquareCollider(false,new AABB(0, 0, 2, 2)));
        Entity e2 = new Entity();
        e2.setCollider(new SquareCollider(false,new AABB(0, 0, 2, 2)));
        
        CollisionDetectorService instance = new CollisionDetectorService();
        
        boolean expResult = false;
        boolean result = instance.collides(e1, e2);
        Assert.assertEquals(expResult, result);
    }
}
