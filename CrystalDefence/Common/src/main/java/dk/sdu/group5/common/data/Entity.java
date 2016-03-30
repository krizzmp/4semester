package dk.sdu.group5.common.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Entity {

    private final UUID ID = UUID.randomUUID();
    private Posf2d velocity;
    private final Set<String> properties;
    private float x;
    private float y;
    private int lives;
    private BoxCollider collider;
    private EntityType type;
    private String texture;
    private float Speed;

    public Entity() {
        velocity = new Posf2d(0f, 0f);
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

    public Posf2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Posf2d velocity) {
        this.velocity = velocity;
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

    public boolean addProperty(String property) {
        return properties.add(property);
    }

    public boolean removeProperty(String property) {
        return properties.remove(property);
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
//        return type + "{x:" + x + ", y:" + y + ", lives:" + lives + "}";
        return String.format("%s{x: %.0f, y: %.0f, lives: %d}", type, x, y, lives);
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

}
