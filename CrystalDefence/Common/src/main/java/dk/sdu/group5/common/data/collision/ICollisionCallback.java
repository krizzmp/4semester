package dk.sdu.group5.common.data.collision;

import dk.sdu.group5.common.data.Entity;

public interface ICollisionCallback {
    void OnCollision(Entity srcEntity);
}
