/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.group5.enemy;

import dk.sdu.group5.common.data.SpawnController;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;

/**
 *
 * @author Klaus
 */
public class EnemyGameProcess implements IGameProcess {

    private EnemySpawner enemySpawner;

    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        enemySpawner = new EnemySpawner();
        SpawnController.getInstance().register(enemySpawner);
    }

    @Override
    public void update(World world, float delta) {

    }

    @Override
    public void stop(World world) {

    }

    @Override
    public void uninstall() {
        SpawnController.getInstance().unRegister(enemySpawner);
    }
}
