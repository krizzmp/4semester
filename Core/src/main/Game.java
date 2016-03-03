package main;


public class Game extends com.badlogic.gdx.Game {

    @Override
    public void create() {
        // TODO: 02/03/16 add initializing logic, such as setting the first screen

        //something like this:
        setScreen(new StartScreen(()->{setScreen(new GameScreen());}));

    }

}
