package dk.sdu.group5.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class StartScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private Runnable onEnter;
    private Skin skin;
    private Stage stage;
    private Table table;

    StartScreen(Runnable onEnter) {
        this.onEnter = onEnter;


    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        System.out.println(Gdx.files.getLocalStoragePath());
        skin = new Skin();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("assets/ui-gray.atlas"));
        skin.addRegions(textureAtlas);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(skin.getDrawable("button_01"), skin.getDrawable("button_01"), skin.getDrawable("button_01"), font);
        final TextButton button = new TextButton("start game", style);
        table.add(button);
        button.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                onEnter.run();
            }
        });
    }

    @Override
    public void render(float delta) {
        //painting the screen white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

}
