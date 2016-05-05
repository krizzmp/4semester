package dk.sdu.group5.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.group5.common.data.Difficulty;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.SpawnController;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.ICollisionSolverService;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import java.util.*;

class GameScreen implements Screen {

    public boolean gameOver = false;
    private PauseScreen PS;
    private SpriteBatch batch;
    private BitmapFont font;
    private World world;
    private List<IGameProcess> processes;
    private List<ICollisionSolverService> collisionSolvers;
    private Texture texture2 = new Texture(Gdx.files.classpath("mapTexture.png"));

    private final Texture defaultTexture;
    private final Map<String, Texture> cachedTextures;

    private Result<IGameProcess> processResult;
    private Result<ICollisionSolverService> collisionSolverResult;

    private final Object processLock = new Object();
    private final Object collisionSolverLock = new Object();

    public GameScreen() {
        FileHandle fileHandle = Gdx.files.classpath("defaultTexture.png");
        if (!fileHandle.exists()) {
            System.err.println("Default texture not found!");
        }
        defaultTexture = new Texture(fileHandle);

        cachedTextures = new HashMap<>();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.CYAN);
        start();
    }

    void start() {
        world = new World(new Difficulty(500, 3)); // spawn every 3 seconds

        synchronized (processLock) {
            processes = new ArrayList<>();
        }

        synchronized (collisionSolverLock) {
            collisionSolvers = new ArrayList<>();
        }

        processResult = Lookup.getDefault().lookupResult(IGameProcess.class);
        processResult.addLookupListener(lookupListenerGameProcess);

        collisionSolverResult = Lookup.getDefault().lookupResult(ICollisionSolverService.class);
        collisionSolverResult.addLookupListener(lookupListenerCollisionSolver);

        synchronized (processLock) {
            processes.addAll(processResult.allInstances());
            processes.forEach((process) -> process.start(world));
        }

        synchronized (collisionSolverLock) {
            collisionSolvers.addAll(collisionSolverResult.allInstances());
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
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
    private final LookupListener lookupListenerCollisionSolver = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {
            Collection<? extends ICollisionSolverService> updatedSolvers = collisionSolverResult.allInstances();

            synchronized (collisionSolverLock) {
                for (ICollisionSolverService solver : updatedSolvers) {
                    if (!collisionSolvers.contains(solver)) {
                        collisionSolvers.add(solver);
                    }
                }
            }

            synchronized (collisionSolverLock) {
                Iterator<ICollisionSolverService> collisionSolverIterator = collisionSolvers.iterator();
                while (collisionSolverIterator.hasNext()) {
                    ICollisionSolverService solver = collisionSolverIterator.next();
                    if (!updatedSolvers.contains(solver)) {
                        collisionSolverIterator.remove();
                    }
                }
            }
        }
    };

    private final LookupListener lookupListenerGameProcess = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {
            Collection<? extends IGameProcess> updatedProcesses = processResult.allInstances();

            synchronized (processLock) {
                for (IGameProcess process : updatedProcesses) {
                    if (!processes.contains(process)) {
                        processes.add(process);
                        process.start(world);
                    }
                }
            }

            synchronized (processLock) {
                Iterator<IGameProcess> processIterator = processes.iterator();
                while (processIterator.hasNext()) {
                    IGameProcess process = processIterator.next();
                    if (!updatedProcesses.contains(process)) {
                        process.stop(world);
                        processIterator.remove();
                    }
                }
            }
        }
    };

    @Override
    public void render(float delta) {

        if (GameKeys.getInstance().pause_backspace.getKeyState() || GameKeys.getInstance().pause_escape.getKeyState()) {

            Game.getInstance().setScreen(PS = new PauseScreen(this));
        }

        //spawn enemies
        // TODO: 29-04-2016 Get out of Common
        SpawnController.getInstance().update(world, delta);

        //update processes
        synchronized (processLock) {
            processes.forEach(p -> p.update(world, delta));
        }

        //update collision solvers
        synchronized (collisionSolverLock) {
            collisionSolvers.forEach(cs -> cs.update(world));
        }

        //render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture2, 0, 0);
        batch.draw(getTexture("mapTexture.png"), 0, 0);
        world.getEntities().forEach(e -> {
            String texturePath = e.getTexturePath();
            if (texturePath != null && !Objects.equals(texturePath, "")) {
                Texture texture = getTexture(texturePath);
                batch.draw(texture, e.getX() - texture.getWidth() / 2f, e.getY() - texture.getHeight() / 2f);
//                font.draw(batch, e.toString(), e.getX() - texture.getWidth() / 2f, e.getY() - texture.getHeight() / 2f);
            }
        });
        font.draw(batch, "" + (1 / delta), 0, font.getLineHeight());
        batch.end();
        if(world.isGameover()){
            Game.getInstance().setScreen(new GameoverScreen());
            dispose();
        }
    }

    private Texture getTexture(String texturePath) {
        if (!cachedTextures.containsKey(texturePath)) {
            FileHandle fileHandle = Gdx.files.classpath(texturePath);
            Texture texture;
            if (fileHandle.exists()) {
                texture = new Texture(fileHandle);
            } else {
                texture = defaultTexture;
            }
            cachedTextures.put(texturePath, texture);
        }

        return cachedTextures.get(texturePath);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void stop() {
        //Stops all processes
        synchronized (processLock) {
            processes.forEach(p -> p.stop(world));
        }
    }
}
