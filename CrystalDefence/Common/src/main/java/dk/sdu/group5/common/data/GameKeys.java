
package dk.sdu.group5.common.data;

import com.badlogic.gdx.Input.Keys;
import java.util.ArrayList;
import java.util.List;


public class GameKeys {
    private static GameKeys instance;
    
    public Key player_movement_up = new Key(Keys.UP, false);
    public Key player_movement_down = new Key(Keys.DOWN, false);
    public Key player_movement_left = new Key(Keys.LEFT, false);
    public Key player_movement_right = new Key(Keys.RIGHT, false);
    public Key player_shoot = new Key(Keys.SPACE, false);
    
    private List<Key> listOfKeys = new ArrayList<>();
    
    public GameKeys() {
        listOfKeys.add(player_movement_up);
        listOfKeys.add(player_movement_down);
        listOfKeys.add(player_movement_left);
        listOfKeys.add(player_movement_right);
        listOfKeys.add(player_shoot);
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
 