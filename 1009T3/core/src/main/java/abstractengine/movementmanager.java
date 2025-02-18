package abstractengine;

import com.badlogic.gdx.Gdx;

public class movementmanager {
	private static final float FALL_SPEED = 150;
	
	public void updateEnemyMovement(entity enemy) {
		enemy.setY(enemy.getY() - FALL_SPEED * Gdx.graphics.getDeltaTime());
	}

}
