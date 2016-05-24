package dk.sdu.group5.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.sdu.group5.core.GameOverScreen;
import dk.sdu.group5.core.GameScreen;
import org.junit.*;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

public class NewGameTest {
    private final Object lock = new Object();
    private LwjglApplication app;
    private Game game;
    private boolean isGameScreen;

    public NewGameTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        LwjglApplicationConfiguration cfg
                = new LwjglApplicationConfiguration();
        cfg.title = "Crystal Defence";
        cfg.x = 0;
        cfg.y = 0;
        cfg.width = 800;
        cfg.height = 400;
        cfg.useGL30 = false;
        cfg.resizable = false;

        game = new Game() {
            @Override
            public void create() {
                GameOverScreen gameOverScreen = new GameOverScreen(this);
                setScreen(gameOverScreen);
            }

            @Override
            public void setScreen(com.badlogic.gdx.Screen screen) {
                super.setScreen(screen);

                if (screen.getClass().equals(GameScreen.class)) {
                    isGameScreen = true;

                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }

        };

        app = new LwjglApplication(game, cfg);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testNewGame() throws FindFailed, InterruptedException {
        Graphics graphics = app.getGraphics();
        Screen screen = new Screen();
        screen.click(new Location(graphics.getWidth() / 2f, graphics.getHeight() / 2f).above(10));

        // Wait for screen change
        synchronized (lock) {
            while (!isGameScreen) {
                lock.wait();
            }
        }

        boolean value = game.getScreen().getClass().equals(GameScreen.class);
        Assert.assertTrue(value);
    }
}
