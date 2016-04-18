package dk.sdu.group5.common.services;

import dk.sdu.group5.common.data.Entity;

public interface ICollisionService {
    boolean collides(Entity ent1, Entity ent2);
}
