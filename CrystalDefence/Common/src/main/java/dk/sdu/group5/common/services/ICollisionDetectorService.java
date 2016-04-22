package dk.sdu.group5.common.services;

import dk.sdu.group5.common.data.Entity;

public interface ICollisionDetectorService {
    boolean collides(Entity ent1, Entity ent2);
}
