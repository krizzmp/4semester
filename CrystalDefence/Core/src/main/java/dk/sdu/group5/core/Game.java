package dk.sdu.group5.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;


class Game extends com.badlogic.gdx.Game {

    private GameScreen gameScreen;
    private StartScreen startScreen;
    private Stage stage;
    private static Game game;
    public static Game getInstance(){
        if (game == null){
            game = new Game();
        }
        return game;
    }
    private Game(){

    }
    
    @Override
    public void create() {
        gameScreen = new GameScreen();
        //something like this:
        startScreen = new StartScreen(() -> {
          //Gdx.app.exit();
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
