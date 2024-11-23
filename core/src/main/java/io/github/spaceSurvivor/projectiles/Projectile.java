// Projectile.java

package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.monsters.Boss;
import io.github.spaceSurvivor.weapons.Weapon;

public abstract class Projectile extends Movable {

    protected float directionX;
    protected float directionY;
    private Weapon weapon;
    private Boss boss;

    public Projectile(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed, Weapon weapon,
            float[] direction) {
        super(texture, posX, posY, sizeX, sizeY, speed);
        this.directionX = direction[0];
        this.directionY = direction[1];
        this.weapon = weapon;
    }

    public Projectile(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed, Boss boss, float[] direction) {
        super(texture, posX, posY, sizeX, sizeY, speed);
        this.directionX = direction[0];
        this.directionY = direction[1];
        this.boss = boss;
    }

    public void move(CollisionManager collisionManager, Map map) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.setPosX(this.getPosX() + this.directionX * this.getSpeed() * deltaTime);
        this.setPosY(this.getPosY() + this.directionY * this.getSpeed() * deltaTime);

        if (this.getPosY() > 100 || this.getPosY() < -100 || this.getPosX() > 100 || this.getPosX() < -100) {
            Entity.entities.remove(this);
            this.dispose();
        } else if (collisionManager.handleEntityMapCollision(this, map)) {
            Entity.entities.remove(this);
            this.dispose();
        }
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public Boss getBoss() {
        return this.boss;
    }

    public float getDamage() {
        if (this.weapon != null) {
            return this.weapon.getDamages();
        } else if (this.boss != null) {
            return this.boss.getDamages();
        } else {
            // Gérer le cas où ni weapon ni boss ne sont définis, par exemple en retournant 0
            return 0;
        }
    }

}
