package dk.sdu.group5.common.data.collision;

import dk.sdu.group5.common.data.Entity;

import java.util.HashMap;
import java.util.Map;

public class CollisionHandler {
    private final Map<ICollider, ICollisionCallback> callbacks;

    public CollisionHandler() {
        callbacks = new HashMap<>();
    }

    public void registerCallback(ICollider collider, ICollisionCallback callback) {
        callbacks.putIfAbsent(collider, callback);
    }

    public void removeCallback(ICollider collider, ICollisionCallback callback) {
        callbacks.remove(collider, callback);
    }

    public void removeCallbacks(ICollider collider) {
        callbacks.remove(collider);
    }

    public void addCollision(ICollider collider, Entity srcEntity) {
        ICollisionCallback callback = callbacks.get(collider);
        if (callback != null) {
            callback.OnCollision(srcEntity);
        }
    }
}
