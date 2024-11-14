package io.github.spaceSurvivor.managers;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.GameScreen;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.projectiles.Projectile;

public class CollisionManager {

    // private Entity entityA;
    // private Entity entityB;

    public CollisionManager() {
        // this.entityA = entityA;
        // this.entityB = entityB;
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

    // public Entity getEntityA() {
    // return this.entityA;
    // }

    // public Entity getEntityB() {
    // return this.entityB;
    // }

}
