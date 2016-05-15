
package dk.sdu.group5.common.data;


public class Key {
    private int keyCode;
    private KeyState state;

    public Key(int keycode) {
        keyCode = keycode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public KeyState getState() {
        return state;
    }

    public void setState(KeyState state) {
        this.state = state;
    }
}
