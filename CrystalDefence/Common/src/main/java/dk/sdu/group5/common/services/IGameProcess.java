package dk.sdu.group5.common.services;

import dk.sdu.group5.common.data.World;

public interface IGameProcess {
    void install();
    void start(World world);
    void update(World world, Float delta);
    void stop(World world);
    void uninstall();
}
