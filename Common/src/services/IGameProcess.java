package services;

import data.World;

public interface IGameProcess {
    void start(World world);
    void update(World world,Float delta);
    void stop(World world);
}
