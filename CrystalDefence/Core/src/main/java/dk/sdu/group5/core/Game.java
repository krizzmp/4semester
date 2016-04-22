package dk.sdu.group5.core;

import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

class Game extends com.badlogic.gdx.Game {

    private GameScreen gameScreen;
    private StartScreen startScreen;
    private Stage stage;

    private static Game game;

    private Game() {

    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    @Override
    public void create() {
        //something like this:
        startScreen = new StartScreen(() -> {
            //Gdx.app.exit();
            gameScreen = new GameScreen();
            gameScreen.start();
            setScreen(gameScreen);
        });
        setScreen(startScreen);
    }

    public void screen(Screen screen) {
        setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        startScreen.dispose();
        gameScreen.dispose();
    }

}
