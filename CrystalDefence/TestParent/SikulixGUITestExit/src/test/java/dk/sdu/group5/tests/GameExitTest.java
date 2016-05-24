package dk.sdu.group5.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.sdu.group5.core.GameScreen;
import dk.sdu.group5.core.StartScreen;
import org.junit.*;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

public class GameExitTest {
    private final Object lock = new Object();
    private LwjglApplication app;
    private Game game;

    public GameExitTest() {
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
                setScreen(new StartScreen(() ->
                {
                    GameScreen gameScreen = new GameScreen(this);
                    game.setScreen(gameScreen);
                }));
            }
        };

        app = new LwjglApplication(game, cfg);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testExitGame() throws FindFailed, InterruptedException {
        TestLifeCycleListener listener = new TestLifeCycleListener();
        app.addLifecycleListener(listener);

        Graphics graphics = app.getGraphics();
        Screen screen = new Screen();
        screen.click(new Location(graphics.getWidth() / 2f,
                graphics.getHeight() / 2f).below(50));

        // Wait for dispose
        synchronized (lock) {
            while (!listener.isDisposed()) {
                lock.wait();
            }
        }

        boolean value = listener.isDisposed();
        Assert.assertTrue(value);
    }

    private class TestLifeCycleListener implements LifecycleListener {
        private boolean disposed;

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void dispose() {
            disposed = true;

            synchronized (lock) {
                lock.notifyAll();
            }
        }

        public boolean isDisposed() {
            return disposed;
        }
    }
}
