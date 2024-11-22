package io.github.spaceSurvivor.managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.projectiles.Projectile;
import io.github.spaceSurvivor.dropable.Xp;
import io.github.spaceSurvivor.Map;

public class CollisionManager {

    public CollisionManager() {
    }

    public void checkAllCollisions() {
        for (int i = 0; i < Entity.entities.size(); i++) {
            for (int j = i + 1; j < Entity.entities.size(); j++) {
                Entity entityA = Entity.entities.get(i);
                Entity entityB = Entity.entities.get(j);

                if (this.isColliding(entityA, entityB)) {
                    this.handleCollision(entityA, entityB);
                }
            }
        }
    }

    public boolean isColliding(Entity entityA, Entity entityB) {
        return entityA.getHitBox().overlaps(entityB.getHitBox());
    }

    private void handlePlayerMonsterCollision(Player player, Monster monster) {
        player.takeDamage(monster.getDamages());
    }

    private void handleMonsterMonsterCollision(Monster monsterA, Monster monsterB) {

    }

    private void handleProjectileMonsterCollision(Projectile projectile, Monster monster) {
        monster.takeDamage(projectile.getDamage());
        Entity.entities.remove(projectile);
        projectile.dispose();
    }

    private void handlePlayerXpCollision(Player player, Xp xp) {
        xp.getAttracted(player);
        player.setXp(xp.getXpValue());
        System.out.println("Player getXp: " + xp.getXpValue());
        System.out.println("Xp total: " + player.getXp());
        player.isLevelGained();
    }

    public boolean handleEntityMapCollision(Movable entity, Map map) {
        TiledMapTileLayer lab = (TiledMapTileLayer) map.getMap().getLayers().get("Lab");
        TiledMapTileLayer rocks = (TiledMapTileLayer) map.getMap().getLayers().get("Rocks");
        TiledMapTileLayer borders = (TiledMapTileLayer) map.getMap().getLayers().get("Borders");

        float[] direction = entity.getDirection();
        Rectangle hitbox = entity.getHitBox();

        // Calculer le point de vérification en fonction de la direction
        float checkX, checkY;

        if (direction[0] > 0) {  // Mouvement vers la droite
            checkX = hitbox.x + hitbox.width;
            checkY = hitbox.y + hitbox.height / 2;
        } else if (direction[0] < 0) {  // Mouvement vers la gauche
            checkX = hitbox.x;
            checkY = hitbox.y + hitbox.height / 2;
        } else if (direction[1] > 0) {  // Mouvement vers le haut
            checkX = hitbox.x + hitbox.width / 2;
            checkY = hitbox.y + hitbox.height;
        } else if (direction[1] < 0) {  // Mouvement vers le bas
            checkX = hitbox.x + hitbox.width / 2;
            checkY = hitbox.y;
        } else {  // Aucun mouvement
            checkX = hitbox.x + hitbox.width / 2;
            checkY = hitbox.y + hitbox.height / 2;
        }

        int tileX = (int) checkX;
        int tileY = (int) checkY;

        TiledMapTileLayer.Cell labCell = lab.getCell(tileX, tileY);
        TiledMapTileLayer.Cell rocksCell = rocks.getCell(tileX, tileY);
        TiledMapTileLayer.Cell bordersCell = borders.getCell(tileX, tileY);


        if (labCell != null && labCell.getTile() != null && labCell.getTile().getId() != 0) {
            return true;
        }
        if (rocksCell != null && rocksCell.getTile() != null && rocksCell.getTile().getId() != 0) {
            return true;
        }
        if (bordersCell != null && bordersCell.getTile() != null && bordersCell.getTile().getId() != 0) {
            return true;
        }

        return false;
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
        } else if ((entityA instanceof Player && entityB instanceof Xp) ||
                (entityA instanceof Xp && entityB instanceof Player)) {
            Player player = (entityA instanceof Player) ? (Player) entityA : (Player) entityB;
            Xp xp = (entityA instanceof Xp) ? (Xp) entityA : (Xp) entityB;
            handlePlayerXpCollision(player, xp);
        }
    }
}
