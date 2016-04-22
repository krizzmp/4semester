package dk.sdu.group5.common.data;

import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.ICollider;
import dk.sdu.group5.common.data.collision.SquareCollider;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Entity {

    private final UUID ID = UUID.randomUUID();
    private final Set<String> properties;
    private float x;
    private float y;
    private int health;
    private ICollider collider;
    private EntityType type;
    private String texture;
    private float Speed;

    public Entity() {
        properties = new HashSet<>();
    }

    public Entity(EntityType entityType, float speed, float x, float y, String texture, int health, float width, float height) {
        this();
        setType(entityType);
        setSpeed(speed);
        setX(x);
        setY(y);
        setTexture(texture);
        setHealth(health);
        setCollider(new SquareCollider(false, new AABB(-width / 2, -height / 2, width, height)));
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

    public Set<String> getProperties() {
        return properties;
    }

    public ICollider getCollider() {
        return collider;
    }

    public void setCollider(ICollider collider) {
        this.collider = collider;
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

    public boolean is(String collidable) {
        return properties.contains(collidable);
    }

    public AABB getBounds() {
        return collider.getBounds();
    }
}
