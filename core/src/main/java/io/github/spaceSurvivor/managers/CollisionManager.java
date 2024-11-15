package io.github.spaceSurvivor.managers;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.projectiles.Projectile;

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

    private void handlePlayerMapCollision(Player player) {

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
