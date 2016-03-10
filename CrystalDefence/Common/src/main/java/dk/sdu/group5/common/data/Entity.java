package dk.sdu.group5.common.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Entity {

    private final UUID ID = UUID.randomUUID();
    private int x, y;
    private int lives;

    private final Set<String> properties;
    private EntityType type;
    private String texture;

    public Entity() {
        properties = new HashSet<>();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void addProperty(String property) throws Exception {
        // TO-BE-RESOLVED: Silent handling, throw exception or return boolean
        // if duplicate found.
        if (!properties.add(property)) {
            throw new Exception("A duplicate entry already exists matching the provided property!");
        }
    }

    public void removeProperty(String property) throws Exception {
        // TO-BE-RESOLVED: Silent handling, throw exception or return boolean
        // if duplicate found.
        if (!properties.remove(property)) {
            throw new Exception("No entry already exists matching the provided property!");
        }
    }

    @Override
    public String toString() {
        return type.toString()+"{x:"+x+", y:"+y+", lives:"+lives+"}";
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }
}
