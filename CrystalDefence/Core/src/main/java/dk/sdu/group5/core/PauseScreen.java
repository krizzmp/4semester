package dk.sdu.group5.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Hivemaster on 31-03-2016.
 */
public class PauseScreen implements Screen{


    private BitmapFont font;
    private Skin skin;
    private Stage stage;
    private Table table;
    private GameScreen game;
    PauseScreen(GameScreen game){
        this.game = game;
    }


    @Override
    public void show() {
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
        addButton("Resume game", ()-> {hide(); game.show(); game.resume();}, style);
        addButton("Main Menu", ()-> mainmenu(), style);
        addButton("Exit game", ()-> Gdx.app.exit(), style);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    public void mainmenu(){
        GameScreen gameScreen;
        StartScreen startScreen;
        gameScreen = new GameScreen();
        //something like this:
        startScreen = new StartScreen(() -> {
//            Gdx.app.exit();
            gameScreen.show(); hide();
        });
        this.hide(); startScreen.show();
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
