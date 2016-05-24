package dk.sdu.group5.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.sdu.group5.core.GameScreen;
import dk.sdu.group5.core.PauseScreen;
import dk.sdu.group5.core.StartScreen;
import org.junit.*;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

public class GameMainMenuTest {
    private final Object lock = new Object();
    private LwjglApplication app;
    private Game game;
    private boolean isStartScreen;

    public GameMainMenuTest() {
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
                GameScreen gameScreen = new GameScreen(this);
                PauseScreen pauseScreen = new PauseScreen(this, gameScreen);
                game.setScreen(pauseScreen);
            }

            @Override
            public void setScreen(com.badlogic.gdx.Screen screen) {
                super.setScreen(screen);

                if (screen.getClass().equals(StartScreen.class)) {
                    isStartScreen = true;

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
    public void testResumeGame() throws FindFailed, InterruptedException {
        Graphics graphics = app.getGraphics();
        Screen screen = new Screen();
        screen.click(new Location(graphics.getWidth() / 2f, graphics.getHeight() / 2f).below(20));

        // Wait for screen change
        synchronized (lock) {
            while (!isStartScreen) {
                lock.wait();
            }
        }

        boolean value = game.getScreen().getClass().equals(StartScreen.class);
        Assert.assertTrue(value);
    }
}
