package dk.sdu.group5.common.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class GameKeys {

    // use this for keycode reference: https://libgdx.badlogicgames.com/nightlies/docs/api/constant-values.html
    private final Key playerMovementUp = new Key(51); // W
    private final Key playerMovementDown = new Key(47); // S
    private final Key playerMovementLeft = new Key(29); // A
    private final Key playerMovementRight = new Key(32); // D
    private final Key playerShootUp = new Key(19); // up
    private final Key playerShootDown = new Key(20); // down
    private final Key playerShootLeft = new Key(21); // left
    private final Key playerShootRight = new Key(22); // right
    private final Key playerPlaceBarrier = new Key(62); // space
    private final Key pauseBackspace = new Key(67);
    private final Key pauseEscape = new Key(131);

    private final Map<Integer, Key> keys = new HashMap<>();

    public GameKeys() {
        keys.put(playerMovementUp.getKeyCode(), playerMovementUp);
        keys.put(playerMovementDown.getKeyCode(), playerMovementDown);
        keys.put(playerMovementLeft.getKeyCode(), playerMovementLeft);
        keys.put(playerMovementRight.getKeyCode(), playerMovementRight);
        keys.put(playerShootUp.getKeyCode(), playerShootUp);
        keys.put(playerShootDown.getKeyCode(), playerShootDown);
        keys.put(playerShootLeft.getKeyCode(), playerShootLeft);
        keys.put(playerShootRight.getKeyCode(), playerShootRight);
        keys.put(playerPlaceBarrier.getKeyCode(), playerPlaceBarrier);
        keys.put(pauseBackspace.getKeyCode(), pauseBackspace);
        keys.put(pauseEscape.getKeyCode(), pauseEscape);
    }

    public Key getPlayerMovementUp() {
        return playerMovementUp;
    }

    public Key getPlayerMovementDown() {
        return playerMovementDown;
    }

    public Key getPlayerMovementLeft() {
        return playerMovementLeft;
    }

    public Key getPlayerMovementRight() {
        return playerMovementRight;
    }

    public Key getPlayerShootUp() {
        return playerShootUp;
    }

    public Key getPlayerShootDown() {
        return playerShootDown;
    }

    public Key getPlayerShootLeft() {
        return playerShootLeft;
    }

    public Key getPlayerShootRight() {
        return playerShootRight;
    }

    public Key getPlayerPlaceBarrier() {
        return playerPlaceBarrier;
    }

    public Key getPauseBackspace() {
        return pauseBackspace;
    }

    public Key getPauseEscape() {
        return pauseEscape;
    }

    public void setKeyState(int keyCode, KeyState state) {
        keys.values().stream().filter(k -> k.getKeyCode() == keyCode).forEach(k -> k.setState(state));
    }

    public KeyState getKeyState(int keyCode) {
        Key k = keys.get(keyCode);
        return k != null ? k.getState() : null;
    }

    public Collection<Key> getKeys() {
        return keys.values();
    }

    public void resetKeys() {
        keys.values().stream().forEach(k -> k.setState(null));
    }
}
 