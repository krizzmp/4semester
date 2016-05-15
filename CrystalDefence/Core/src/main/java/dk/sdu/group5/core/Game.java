package dk.sdu.group5.core;

import com.badlogic.gdx.Screen;

class Game extends com.badlogic.gdx.Game {

    private StartScreen startScreen;
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
            GameScreen gameScreen = new GameScreen();
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

        if (startScreen != null) {
            startScreen.dispose();
        }
    }

}
