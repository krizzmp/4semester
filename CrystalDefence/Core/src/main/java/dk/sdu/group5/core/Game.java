package dk.sdu.group5.core;

public class Game extends com.badlogic.gdx.Game {

    private StartScreen startScreen;
    
    @Override
    public void create() {
        //something like this:
        startScreen = new StartScreen(() -> {
            //Gdx.app.exit();
            GameScreen gameScreen = new GameScreen(this);
            setScreen(gameScreen);
        });
        setScreen(startScreen);
    }

    @Override
    public void dispose() {
        super.dispose();

        if (startScreen != null) {
            startScreen.dispose();
        }
    }

}
