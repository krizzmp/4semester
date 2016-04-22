package dk.sdu.group5.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

class StartScreen implements Screen {

    private BitmapFont font;
    private final Runnable onEnter;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButtonStyle style;

    StartScreen(Runnable onEnter) {
        this.onEnter = onEnter;

        font = new BitmapFont();
        font.setColor(Color.RED);
        stage = new Stage();

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("assets/ui-gray.atlas"));
        skin = new Skin();
        skin.addRegions(textureAtlas);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        style = new TextButtonStyle(skin.getDrawable("button_01"), skin.getDrawable("button_01"), skin.getDrawable("button_01"), font);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        addButton("start game", onEnter, style);
        addButton("exit game", () -> Gdx.app.exit(), style);
    }

    private void addButton(String text, Runnable onEnter, TextButtonStyle style) {
        TextButton button = new TextButton(text, style);
        button.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                onEnter.run();
            }
        });
        table.add(button);
        table.row();
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
        font.dispose();
        stage.dispose();
        skin.dispose();
    }

}
