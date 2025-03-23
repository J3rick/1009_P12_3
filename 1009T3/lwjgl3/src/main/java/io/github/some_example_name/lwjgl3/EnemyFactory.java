package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.math.MathUtils;

public class EnemyFactory {
    private String[] enemyTextureFiles;
    
    public EnemyFactory(String[] enemyTextureFiles) {
        this.enemyTextureFiles = enemyTextureFiles;
    }
    
    public enum EnemyType {
        VERTICAL,
        HORIZONTAL
    }
    
    // Create enemy based on type
    public enemy createEnemy(EnemyType type, int id, float x, float y) {
        switch(type) {
            case VERTICAL:
                return createVerticalEnemy(id, x, y);
            case HORIZONTAL:
                return createHorizontalEnemy(id, x, y);
            default:
                return createVerticalEnemy(id, x, y);
        }
    }
    
    // Create a random vertical enemy with random texture
    private enemy createVerticalEnemy(int id, float x, float y) {
        int randomIndex = MathUtils.random(0, enemyTextureFiles.length - 1);
        String chosenEnemyTextureFile = enemyTextureFiles[randomIndex];
        return new enemy(id, chosenEnemyTextureFile, x, y, enemy.MovementType.VERTICAL);
    }
    
    // Create a horizontal enemy
    private enemy createHorizontalEnemy(int id, float x, float y) {
        String chosenEnemyTextureFile = "enemy.png"; // Default horizontal enemy texture
        enemy horizontalEnemy = new enemy(id, chosenEnemyTextureFile, x, y, enemy.MovementType.HORIZONTAL);
        horizontalEnemy.setMovingRight(true);
        return horizontalEnemy;
    }
}
