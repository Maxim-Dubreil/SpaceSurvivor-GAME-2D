package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.monsters.Boss;

public class BossProjectiles extends Projectile {
    public BossProjectiles(float posX, float posY, Boss boss, Player player) {
        super(new Texture("Projectile/BossFireProjectile.png"), posX * Map.getTileSize(), posY * Map.getTileSize(), 30, 30,
            500, boss, new float[] { 0, 0 });

        float playerX = player.getPosX();
        float playerY = player.getPosY();

        Vector2 directionVector = new Vector2(playerX - posX, playerY - posY).nor();

        this.directionX = directionVector.x;
        this.directionY = directionVector.y;
    }
}
