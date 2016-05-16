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
import dk.sdu.group5.common.data.*;
import dk.sdu.group5.common.services.ICollisionDetectorService;
import dk.sdu.group5.common.services.ICollisionSolverService;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import java.util.*;
import java.util.stream.Collectors;

class GameScreen implements Screen {

    public boolean gameOver = false;
    private PauseScreen pauseScreen;
    private SpriteBatch batch;
    private BitmapFont font;
    private World world;
    private List<IGameProcess> processes;
    private ICollisionSolverService collisionSolver;
    private ICollisionDetectorService collisionDetector;

    private final Texture defaultTexture;
    private Map<String, Texture> cachedTextures;

    private Result<IGameProcess> processResult;
    private Result<ICollisionSolverService> collisionSolverResult;
    private Result<ICollisionDetectorService> collisionDetectorResult;

    private final Object processLock = new Object();
    private final Object collisionDetectorLock = new Object();
    private final Object collisionSolverLock = new Object();

    public GameScreen() {
        FileHandle fileHandle = Gdx.files.classpath("defaultTexture.png");
        if (!fileHandle.exists()) {
            System.err.println("Default texture not found!");
        }

        defaultTexture = new Texture(fileHandle);
        pauseScreen = new PauseScreen(this);
        cachedTextures = new HashMap<>();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.CYAN);

        world = new World(new Difficulty(500, 3)); // spawn every 3 seconds
        world.setDisplayResolutionWidth(Gdx.graphics.getWidth());
        world.setDisplayResolutionHeight(Gdx.graphics.getHeight());

        world.setGameKeys(new GameKeys());
        world.setOldGameKeys(new GameKeys());

        synchronized (processLock) {
            processes = new ArrayList<>();
        }

        processResult = Lookup.getDefault().lookupResult(IGameProcess.class);
        processResult.addLookupListener(lookupListenerGameProcess);

        collisionSolverResult = Lookup.getDefault().lookupResult(ICollisionSolverService.class);
        collisionSolverResult.addLookupListener(lookupListenerCollisionSolver);

        collisionDetectorResult = Lookup.getDefault().lookupResult(ICollisionDetectorService.class);
        collisionDetectorResult.addLookupListener(lookupListenerCollisionDetector);

        synchronized (processLock) {
            processes.addAll(processResult.allInstances());
            processes.forEach((process) -> process.start(world));
        }

        updateDetector();
        updateSolver();
    }

    @Override
    public void show() {
        if (world != null) {
            world.getGameKeys().resetKeys();
            world.getOldGameKeys().resetKeys();
        }
                
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int k) {
                world.getGameKeys().setKeyState(k, KeyState.PRESSED);
                return true;
            }

            @Override
            public boolean keyUp(int k) {
                world.getGameKeys().setKeyState(k, KeyState.RELEASED);
                return true;
            }
        });

    }

    private final LookupListener lookupListenerCollisionSolver = le -> {
        updateSolver();
    };

    private final LookupListener lookupListenerCollisionDetector = le -> {
        updateDetector();
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

    private void updateDetector() {
        synchronized (collisionDetectorLock) {
            collisionDetector = findDetector();
        }
    }

    private void updateSolver() {
        synchronized (collisionSolverLock) {
            collisionSolver = findSolver();
        }
    }

    private ICollisionDetectorService findDetector() {
        Optional<? extends ICollisionDetectorService> optionalDetector;
        optionalDetector = collisionDetectorResult.allInstances().stream().findFirst();
        if (optionalDetector.isPresent()) {
            return optionalDetector.get();
        }

        return null;
    }

    private ICollisionSolverService findSolver() {
        Optional<? extends ICollisionSolverService> optionalSolver;
        optionalSolver = collisionSolverResult.allInstances().stream().findFirst();
        if (optionalSolver.isPresent()) {
            return optionalSolver.get();
        }

        return null;
    }

    @Override
    public void render(float delta) {

        if (world.isGameover()) {
            Game.getInstance().setScreen(new GameoverScreen());
            dispose();
        }

        updateInput();
        updateServices(delta);
        draw(delta);
    }

    private void updateInput() {

        for (Key key : world.getGameKeys().getKeys()) {
            if (world.getOldGameKeys().getKeyState(key.getKeyCode()) == KeyState.PRESSED) {
                key.setState(KeyState.HELD);
            }

            if (world.getOldGameKeys().getKeyState(key.getKeyCode()) == KeyState.RELEASED) {
                key.setState(KeyState.NONE);
            }

            world.getOldGameKeys().setKeyState(key.getKeyCode(), key.getState());
        }

        if (world.getGameKeys().getPauseBackspace().getState() == KeyState.RELEASED
                || world.getGameKeys().getPauseEscape().getState() == KeyState.RELEASED) {

            Game.getInstance().setScreen(pauseScreen);
        }
    }

    private void updateServices(float delta) {
        //update processes
        synchronized (processLock) {
            processes.forEach(p -> p.update(world, delta));
        }

        //update collisions
        world.clearCollisions();

        synchronized (collisionDetectorLock) {
            if (collisionDetector != null) {
                List<Entity> collidableEnts = world.getEntities().stream()
                        .filter(e -> e.getCollider() != null && e.is("collidable"))
                        .collect(Collectors.toList());

                List<Entity> dynamicEnts = collidableEnts.stream()
                        .filter(e -> !e.getProperties().contains("static"))
                        .collect(Collectors.toList());

                dynamicEnts.stream().forEach(de -> collidableEnts.stream().filter(ce -> de != ce
                        && collisionDetector.collides(de, ce)).forEach(ce -> {
                    world.addCollision(de, ce);
                    if (ce.is("static")) {
                        world.addCollision(ce, de);
                    }
                }));
            }
        }

        synchronized (collisionSolverLock) {
            if (collisionSolver != null) {
                world.getEntities().forEach(entity -> {
                    world.getCollisions(entity).forEach(collidedEntity -> {
                        collisionSolver.solve(entity, collidedEntity);
                    });
                });
            }
        }
    }

    private void draw(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (world.getBackgroundTexturePath() != null) {
            batch.draw(getTexture(world.getBackgroundTexturePath()), 0, 0);
        }
        world.getEntities().forEach(e -> {
            String texturePath = e.getTexturePath();
            if (texturePath != null && !Objects.equals(texturePath, "")) {
                Texture texture = getTexture(texturePath);
                batch.draw(texture, e.getX() - texture.getWidth() / 2f, e.getY() - texture.getHeight() / 2f);
//                font.draw(batch, e.toString(), e.getX() - texture.getWidth() / 2f, e.getY() - texture.getHeight() / 2f);
            }
        });

        font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(),
                world.getDisplayResolutionWidth() - 20f,
                world.getDisplayResolutionHeight() - font.getLineHeight());

        world.getEntities().stream().filter(e -> e.getType() == EntityType.PLAYER)
                .findFirst().ifPresent(player -> {
            font.draw(batch, "Player Health: " + player.getHealth(),
                    0, world.getDisplayResolutionHeight() - font.getLineHeight());
        });

        world.getEntities().stream().filter(e -> e.getType() == EntityType.TOWER)
                .findFirst().ifPresent(tower -> {
            font.draw(batch, "Tower Health: " + tower.getHealth(),
                    0, world.getDisplayResolutionHeight() - font.getLineHeight() * 2f);
        });
        
        batch.end();
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
        //Stops all processes
        synchronized (processLock) {
            processes.forEach(p -> p.stop(world));
        }
    }
}
