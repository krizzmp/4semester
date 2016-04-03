
package dk.sdu.group5.common.data;

import java.util.ArrayList;
import java.util.List;


public class GameKeys {
    private static GameKeys instance;
    
    // use this for keycode reference: https://libgdx.badlogicgames.com/nightlies/docs/api/constant-values.html
    public Key player_movement_up = new Key(51, false);
    public Key player_movement_down = new Key(47, false);
    public Key player_movement_left = new Key(29, false);
    public Key player_movement_right = new Key(32, false);
    public Key player_shoot_up = new Key(19, false);
    public Key player_shoot_down = new Key(20, false);
    public Key player_shoot_left = new Key(21, false);
    public Key player_shoot_right = new Key(22, false);
    public Key player_place_barrier = new Key(62, false);
    
    private List<Key> listOfKeys = new ArrayList<>();
    
    public GameKeys() {
        listOfKeys.add(player_movement_up);
        listOfKeys.add(player_movement_down);
        listOfKeys.add(player_movement_left);
        listOfKeys.add(player_movement_right);
        listOfKeys.add(player_shoot_up);
        listOfKeys.add(player_shoot_down);
        listOfKeys.add(player_shoot_left);
        listOfKeys.add(player_shoot_right); 
        listOfKeys.add(player_place_barrier);
    }
    
    public static GameKeys getInstance() {
        if (instance == null)
            instance = new GameKeys();
        return instance;
    }

    public void setKeyState(int key, boolean state) {
        for(Key k : listOfKeys) {
            if(k.getKey() == key) {
                k.setState(state);
            }
        }
    }
}
 