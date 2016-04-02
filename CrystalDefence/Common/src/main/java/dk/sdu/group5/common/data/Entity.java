package dk.sdu.group5.common.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Entity {

    private final UUID ID = UUID.randomUUID();
    private final Set<String> properties;
    private float x;
    private float y;
    private int health;
    private EntityType type;
    private String texture;
    private float Speed;

    public Entity() {
        properties = new HashSet<>();
    }

    public float getSpeed() {
        return Speed;
    }

    public void setSpeed(float speed) {
        Speed = speed;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean addProperty(String property) {
        return properties.add(property);
    }

    public boolean removeProperty(String property) {
        return properties.remove(property);
    }

    @Override
    public String toString() {
//        return type + "{x:" + x + ", y:" + y + ", health:" + health + "}";
        return String.format("%s{x: %.0f, y: %.0f, health: %d}", type, x, y, health);
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

}
