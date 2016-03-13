package dk.sdu.group5.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.Lookup;

import java.util.Collection;
import java.util.Objects;


class GameScreen implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private World world;
    private Collection<? extends IGameProcess> processes;
    public static final int GAME_READY = 0; 
    public static final int GAME_RUNNING = 1; 
    public static final int GAME_PAUSED = 2; 
    public static final int GAME_OVER = 4;
    public static int state;

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.CYAN);
        processes = Lookup.getDefault().lookupAll(IGameProcess.class);
        world = new World();
        processes.forEach((p) -> p.start(world));
        world.getEntities().forEach(System.out::println);
        state = GAME_RUNNING;
     
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // TODO: 03/03/16 add game rendering

        //update entities
        processes.forEach((p) -> p.update(world, delta));

        //render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
//        font.draw(batch, world.getEntities().toString(), 150, 220);
        world.getEntities().forEach(e -> {
            String texture = e.getTexture();
            if (texture != null && !Objects.equals(texture, "")) {
                batch.draw(new Texture(Gdx.files.classpath(texture)), e.getX(), e.getY());
                font.draw(batch,e.toString(),e.getX(),e.getY());
            }
        });
        batch.end();
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
        updatePaused();
           
    }
    
    

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
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
    
    @Overide
    public void update() {
        switch (state) {
    case GAME_READY:
        updateReady();
        break;
    case GAME_RUNNING:
        updateRunning(delta);
        break;
    case GAME_PAUSED:
        updatePaused();
        break;
    case GAME_OVER:
        gameOver = true;
        updateGameOver();
        break;
    }
    }
    public void updatePaused(){
        state = GAME_PAUSED;
    }
}
