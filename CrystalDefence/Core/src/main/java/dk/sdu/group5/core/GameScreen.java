package dk.sdu.group5.core;

import com.badlogic.gdx.*;
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
        Gdx.input.setInputProcessor (new InputAdapter() {
            public boolean keyDown(int keycode) {
                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK))
                    if (state == state.PAUSE){
                    state = state.RUN;
                }else{
                    state = state.PAUSE;
                }

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
        // TODO: 03/03/16 add game rendering

        //update entities
        processes.forEach((p) -> p.update(world, delta));

        //render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        if(stage != null){
            stage.clear();
        }
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
    public void updatePaused(){
        font = new BitmapFont();
        font.setColor(Color.RED);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("assets/ui-gray.atlas"));
        skin = new Skin();
        skin.addRegions(textureAtlas);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(skin.getDrawable("button_01"), skin.getDrawable("button_01"), skin.getDrawable("button_01"), font);
        addButton("Resume game", ()->state = state.RUN, style);
        addButton("Exit game", ()->Gdx.app.exit(), style);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }

    private void addButton(String text, final Runnable onEnter, TextButton.TextButtonStyle style) {
        final TextButton button = new TextButton(text, style);
        button.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                onEnter.run();
            }
        });
        table.add(button);
        table.row();

    }

}
