package dk.sdu.group5.common.data.collision;

import dk.sdu.group5.common.data.Entity;

public interface ICollisionCallback {
    void onCollision(Entity srcEntity);
}
