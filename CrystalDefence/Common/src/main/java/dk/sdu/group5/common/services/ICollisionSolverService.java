package dk.sdu.group5.common.services;

import dk.sdu.group5.common.data.Entity;

public interface ICollisionSolverService {
    void solve(Entity ent1, Entity ent2);
}
