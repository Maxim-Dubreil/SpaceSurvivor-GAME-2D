package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.monsters.Boss;
import io.github.spaceSurvivor.weapons.Weapon;

/**
 * Abstract class representing a projectile in the game.
 * Projectiles can be fired by weapons or enemies like the Boss.
 */
public abstract class Projectile extends Movable {

    /** X component of the projectile's direction. */
    protected float directionX;

    /** Y component of the projectile's direction. */
    protected float directionY;

    /** The weapon that fired the projectile, if applicable. */
    private Weapon weapon;

    /** The boss that fired the projectile, if applicable. */
    private Boss boss;

    /**
     * Constructs a new Projectile fired by a weapon.
     *
     * @param texture   The texture of the projectile.
     * @param posX      The initial X position.
     * @param posY      The initial Y position.
     * @param sizeX     The width of the projectile.
     * @param sizeY     The height of the projectile.
     * @param speed     The speed of the projectile.
     * @param weapon    The weapon that fired the projectile.
     * @param direction The direction vector [X, Y].
     */
    public Projectile(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed, Weapon weapon,
            float[] direction) {
        super(texture, posX, posY, sizeX, sizeY, speed);
        this.directionX = direction[0];
        this.directionY = direction[1];
        this.weapon = weapon;
    }

    /**
     * Constructs a new Projectile fired by the boss.
     *
     * @param texture   The texture of the projectile.
     * @param posX      The initial X position.
     * @param posY      The initial Y position.
     * @param sizeX     The width of the projectile.
     * @param sizeY     The height of the projectile.
     * @param speed     The speed of the projectile.
     * @param boss      The boss that fired the projectile.
     * @param direction The direction vector [X, Y].
     */
    public Projectile(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed, Boss boss,
            float[] direction) {
        super(texture, posX, posY, sizeX, sizeY, speed);
        this.directionX = direction[0];
        this.directionY = direction[1];
        this.boss = boss;
    }

    /**
     * Moves the projectile and handles collisions with the map.
     *
     * @param collisionManager The collision manager.
     * @param map              The game map.
     */
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

    /**
     * Gets the weapon that fired the projectile.
     *
     * @return The weapon, or null if not applicable.
     */
    public Weapon getWeapon() {
        return this.weapon;
    }

    /**
     * Gets the boss that fired the projectile.
     *
     * @return The boss, or null if not applicable.
     */
    public Boss getBoss() {
        return this.boss;
    }

    /**
     * Gets the damage dealt by the projectile.
     *
     * @return The damage value.
     */
    public float getDamage() {
        if (this.weapon != null) {
            return this.weapon.getDamages();
        } else if (this.boss != null) {
            return this.boss.getDamages();
        } else {
            // Default damage if neither weapon nor boss is set
            return 0;
        }
    }
}
