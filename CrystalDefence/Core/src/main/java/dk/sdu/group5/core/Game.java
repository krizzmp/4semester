package dk.sdu.group5.core;


import com.badlogic.gdx.Gdx;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

class Game extends com.badlogic.gdx.Game {

    private GameScreen gameScreen;
    private StartScreen startScreen;

    @Override
    public void create() {
        // TODO: 02/03/16 add initializing logic, such as setting the first screen
        gameScreen = new GameScreen();
        //something like this:
        startScreen = new StartScreen(() -> {
//            Gdx.app.exit();
            setScreen(gameScreen);
        });
        setScreen(startScreen);


    }

    @Override
    public void dispose() {
        super.dispose();
        startScreen.dispose();
        gameScreen.dispose();
    }

}
