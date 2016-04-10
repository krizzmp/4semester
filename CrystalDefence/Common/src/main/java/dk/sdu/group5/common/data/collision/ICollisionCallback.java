package dk.sdu.group5.common.data.collision;

import dk.sdu.group5.common.data.Entity;

interface ICollisionCallback {
    void onCollision(Entity srcEntity);
}
