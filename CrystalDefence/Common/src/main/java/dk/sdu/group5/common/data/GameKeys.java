
package dk.sdu.group5.common.data;

import java.util.ArrayList;
import java.util.List;


public class GameKeys {
    private static GameKeys instance;
    
    // use this for keycode reference: https://libgdx.badlogicgames.com/nightlies/docs/api/constant-values.html
    public final Key player_movement_up = new Key(51, false); // W
    public final Key player_movement_down = new Key(47, false); // S
    public final Key player_movement_left = new Key(29, false); // A
    public final Key player_movement_right = new Key(32, false); // D
    public final Key player_shoot_up = new Key(19, false); // up
    public final Key player_shoot_down = new Key(20, false); // down
    public final Key player_shoot_left = new Key(21, false); // left
    public final Key player_shoot_right = new Key(22, false); // right
    public final Key player_place_barrier = new Key(62, false); // space
    public final Key pause_backspace = new Key(67, false);
    public final Key pause_escape = new Key(131, false);

    private final List<Key> listOfKeys = new ArrayList<>();
    
    private GameKeys() {
        listOfKeys.add(player_movement_up);
        listOfKeys.add(player_movement_down);
        listOfKeys.add(player_movement_left);
        listOfKeys.add(player_movement_right);
        listOfKeys.add(player_shoot_up);
        listOfKeys.add(player_shoot_down);
        listOfKeys.add(player_shoot_left);
        listOfKeys.add(player_shoot_right); 
        listOfKeys.add(player_place_barrier);
        listOfKeys.add(pause_backspace);
        listOfKeys.add(pause_escape);
    }
    
    public static GameKeys getInstance() {
        if (instance == null)
            instance = new GameKeys();
        return instance;
    }

    public void setKeyState(int key, boolean state) {
        listOfKeys.stream().filter(k -> k.getKey() == key).forEach(k -> k.setState(state));
    }

    public void resetKeys(){
        listOfKeys.stream().forEach(k -> k.setState(false));
    }
}
 