package dk.sdu.group5.core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dk.sdu.group5.common.data.Difficulty;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.SpawnController;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.CollisionController;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.Lookup;

import java.util.Collection;
import java.util.Objects;

class GameScreen implements Screen {
    private PauseScreen PS;
    private SpriteBatch batch;
    private BitmapFont font;
    private World world;
    private Collection<? extends IGameProcess> processes;
    public boolean gameOver = false;
    private Skin skin;
    private Stage stage;
    private Table table;
    public enum State
    {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
    }
    private State state = State.RUN;
    CollisionController collisionController = new CollisionController();

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        PS = new PauseScreen(this);
        state = State.RUN;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.CYAN);
        processes = Lookup.getDefault().lookupAll(IGameProcess.class);
        world = new World(new Difficulty(500, 3)); // spawn every 3 seconds
        processes.forEach(p -> p.start(world));
        world.getEntities().forEach(System.out::println);
        Gdx.input.setInputProcessor (new InputAdapter() {
            public boolean keyDown(int k) {
                GameKeys.getInstance().setKeyState(k, true);
                return true;
            }

            @Override
            public boolean keyUp(int k) {
                //Searches the list of all used keys, and returns true if that key is in that list
                GameKeys.getInstance().setKeyState(k, false);
                return true;
            }
        });
    }


    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        if(GameKeys.getInstance().pause_backspace.getKeyState() || GameKeys.getInstance().pause_escape.getKeyState()){
            PS = new PauseScreen(this);
            Game.getInstance().setScreen(PS);
        }
        //spawn enemies
        SpawnController.getInstance().update(world, delta);

        //update entities
        processes.forEach(p -> p.update(world, delta));

        //render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        world.getEntities().forEach(e -> {
            String texturePath = e.getTexture();
            if (texturePath != null && !Objects.equals(texturePath, "")) {
                Texture texture = new Texture(Gdx.files.classpath(texturePath));
                batch.draw(texture, e.getX() - texture.getWidth() / 2f, e.getY() - texture.getHeight() / 2f);
                font.draw(batch, e.toString(), e.getX() - texture.getWidth() / 2f, e.getY() - texture.getHeight() / 2f);
            }
        });
        batch.end();
        update();
    }

    /**
     *
     */

    /**
     * @param width  the width of the window
     * @param height the height of the window
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {
        if (state == state.RUN) state = state.PAUSE;

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {
        if (state == state.PAUSE) state = state.RUN;
    }

    /**
     * Called when this screen is no longer the current screen for a
     * {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }


    public void update() {
        switch (state) {
    case RUN:
        updateRunning();
        break;
    case PAUSE:
        updatePaused();
        break;

    }}



    public void updateRunning(){

    }
    public void updatePaused(){

        PS = new PauseScreen(this);
        hide();
        PS.show();


    }


}
