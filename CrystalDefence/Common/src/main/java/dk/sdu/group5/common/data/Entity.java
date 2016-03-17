package dk.sdu.group5.common.data;

import java.util.*;

public class Entity {

    private final UUID ID = UUID.randomUUID();
    private float x, y;
    private int lives;

    private final Set<String> properties;
    private BoxCollider collider;
    private EntityType type;
    private String texture;

    public Entity() {
        properties = new HashSet<>();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
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

    public Set<String> getProperties() {
        return properties;
    }

    public BoxCollider getCollider() {
        return collider;
    }

    public void setCollider(BoxCollider collider) {
        this.collider = collider;
    }

    @Override
    public String toString() {
        return type +"{x:"+x+", y:"+y+", lives:"+lives+"}";
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }
}
