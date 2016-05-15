package dk.sdu.group5.common.data;

public class Difficulty {
    final int maxConcurrentDifficulty;
    private int currentDifficulty;
    private int numberOfEnemiesToKillInOrderToWin;
    private int numberOfEnemiesKilled;
    final float spawnRate;

    public Difficulty( int maxConcurrentDifficulty, float spawnRate) {
        this.maxConcurrentDifficulty = maxConcurrentDifficulty;
        currentDifficulty = 0;
        this.spawnRate = spawnRate;
    }

    public int getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(int currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public int getNumberOfEnemiesToKillInOrderToWin() {
        return numberOfEnemiesToKillInOrderToWin;
    }

    public void setNumberOfEnemiesToKillInOrderToWin(int numberOfEnemiesToKillInOrderToWin) {
        this.numberOfEnemiesToKillInOrderToWin = numberOfEnemiesToKillInOrderToWin;
    }

    public int getNumberOfEnemiesKilled() {
        return numberOfEnemiesKilled;
    }

    public void setNumberOfEnemiesKilled(int numberOfEnemiesKilled) {
        this.numberOfEnemiesKilled = numberOfEnemiesKilled;
    }

    public int getMaxConcurrentDifficulty() {
        return maxConcurrentDifficulty;
    }

    public float getSpawnRate() {
        return spawnRate;
    }
}
