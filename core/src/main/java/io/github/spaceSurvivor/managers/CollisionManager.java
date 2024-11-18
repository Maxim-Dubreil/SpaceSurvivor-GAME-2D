package io.github.spaceSurvivor.managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.projectiles.Projectile;
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
        if (entityA.getHitBox().overlaps(entityB.getHitBox())) {
            return true;
        } else {
            return false;
        }
    }

    private void handlePlayerMonsterCollision(Player player, Monster monster) {
        player.takeDamage(monster.getDamages());
        System.out.println("Player collided with a Monster!");

    }

    private void handleMonsterMonsterCollision(Monster monsterA, Monster monsterB) {

    }

    private void handleProjectileMonsterCollision(Projectile projectile, Monster monster) {
        monster.takeDamage(projectile.getDamage());
        Entity.entities.remove(projectile);
        projectile.dispose();
        System.out.println("Projectile a touché le Monster!");
    }

    public boolean handleEntityMapCollision(Movable entity, Map map) {

        TiledMapTileLayer lab = (TiledMapTileLayer) map.getMap().getLayers().get("Lab");
        TiledMapTileLayer rocks = (TiledMapTileLayer) map.getMap().getLayers().get("Rocks");
        TiledMapTileLayer borders = (TiledMapTileLayer) map.getMap().getLayers().get("Borders");
        float x = entity.getPosX();
        float y = entity.getPosY();

        int tileX = (int) x;
        int tileY = (int) y;

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
        }

    }

}
