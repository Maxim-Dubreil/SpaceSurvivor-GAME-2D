package io.github.spaceSurvivor.managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.projectiles.Projectile;
import io.github.spaceSurvivor.Map;

public class CollisionManager {

    public CollisionManager() {

    }

    public boolean isColliding(Entity entityA, Entity entityB) {
        if (entityA.getHitBox().overlaps(entityB.getHitBox())) {
            return true;
        } else {
            return false;
        }
    }

    private void handlePlayerMonsterCollision(Player player, Monster monster) {
        System.out.println("Player collided with a Monster!");

    }

    private void handleMonsterMonsterCollision(Monster monsterA, Monster monsterB) {
        System.out.println("Monster and Monster collided!");

    }

    private void handleProjectileMonsterCollision(Projectile projectile, Monster monster) {
        System.out.println("Projectile a touché le Monster!");
        Entity.entities.remove(projectile);
        projectile.dispose();
        Entity.entities.remove(monster);
        monster.dispose();
    }

    public boolean handleMapCollision(Player player, Map map) {
        TiledMapTileLayer temple = (TiledMapTileLayer) map.getMap().getLayers().get("Temple");
        float x = player.getPosX();
        float y = player.getPosY();

        int tileX = (int) x;
        int tileY = (int) y;

        TiledMapTileLayer.Cell cell = temple.getCell(tileX, tileY);
        if (cell != null){
            int tileId = cell.getTile().getId();
            if(tileId != 0){
                System.out.println("Player collided with a wall!");
                return true;
            }
        }

        return false;
    }

    private void handlePlayerObjectCollision(Player player) {

    }

    public void handleCollision(Entity entityA, Entity entityB) {
        if ((entityA instanceof Player && entityB instanceof Monster) ||
                (entityA instanceof Monster && entityB instanceof Player)) {
            Player player = (entityA instanceof Player) ? (Player) entityA : (Player) entityB;
            Monster monster = (entityA instanceof Monster) ? (Monster) entityA : (Monster) entityB;
            handlePlayerMonsterCollision(player, monster);
        } else if ((entityA instanceof Projectile && entityB instanceof Monster) ||
                (entityA instanceof Monster && entityB instanceof Projectile)) {
            Projectile projectile = (entityA instanceof Projectile) ? (Projectile) entityA : (Projectile) entityB;
            Monster monster = (entityA instanceof Monster) ? (Monster) entityA : (Monster) entityB;
            handleProjectileMonsterCollision(projectile, monster);
        } else if (entityA instanceof Monster && entityB instanceof Monster) {
            Monster monster1 = (Monster) entityA;
            Monster monster2 = (Monster) entityB;
            handleMonsterMonsterCollision(monster1, monster2);
        }

    }

}
