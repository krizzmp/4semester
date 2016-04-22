package dk.sdu.group5.common.data;

public class Difficulty {
    final int maxConcurrentDifficulty;
    int currentDifficulty;
    int numberOfEnemiesToKillInOrderToWin;
    int numberOfEnemiesKilled;
    final float spawnRate;

    public Difficulty( int maxConcurrentDifficulty, float spawnRate) {
        this.maxConcurrentDifficulty = maxConcurrentDifficulty;
        currentDifficulty = 0;
        this.spawnRate = spawnRate;
    }
}
