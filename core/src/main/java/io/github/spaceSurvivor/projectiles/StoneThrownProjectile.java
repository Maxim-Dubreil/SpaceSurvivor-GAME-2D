package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.weapons.Weapon;

public class StoneThrownProjectile extends Projectile {

    private int range;

    public StoneThrownProjectile(float posX, float posY, float[] direction, Weapon weapon, int range) {
        super(new Texture("Projectile/stoneProjectile.png"), posX * Map.getTileSize(), posY * Map.getTileSize(), 15,
                15,
                300, weapon, direction);
        this.range = range;
    }

    @Override
    public void move(CollisionManager collisionManager, Map map) {
        Entity nearestEnemy = findNearestEnemy();
        if (nearestEnemy != null) {
            float deltaX = nearestEnemy.getPosX() - this.getPosX();
            float deltaY = nearestEnemy.getPosY() - this.getPosY();
            float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            this.directionX = deltaX / length;
            this.directionY = deltaY / length;
        }

        super.move(collisionManager, map);
    }

    private Entity findNearestEnemy() {
        Entity nearest = null;
        float minDistance = this.range;

        for (Entity entity : Entity.entities) {
            if (!entity.isEnemy()) {
                continue;
            }

            float deltaX = entity.getPosX() - Player.posX;
            float deltaY = entity.getPosY() - Player.posY;
            float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance < minDistance) {
                minDistance = distance;
                nearest = entity;
            }
        }

        return nearest;
    }

    public static boolean isEnemyNearby(float playerX, float playerY, int maxDistance) {
        for (Entity entity : Entity.entities) {
            if (!entity.isEnemy()) {
                continue;
            }

            float deltaX = entity.getPosX() - playerX;
            float deltaY = entity.getPosY() - playerY;
            float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance <= maxDistance) {
                return true;
            }
        }
        return false;
    }
}
