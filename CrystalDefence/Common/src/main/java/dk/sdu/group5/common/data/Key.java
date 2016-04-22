
package dk.sdu.group5.common.data;


public class Key {
    private int key;
    private boolean state;
    
    public Key(int keycode, boolean state){
        key = keycode;
        this.state = state;
    }
    
    public int getKey() {
        return key;
    }
    
    public boolean getKeyState() {
        return state;
    }
    
    public void setKey(int keycode) {       // Should probably also check the key is a viable ligGDX keycode 
        key = keycode;
    }
    
    public void setState(boolean state) {
        this.state = state;
    }
}
