package io.github.spaceSurvivor.monsters;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.dropable.Xp;

/**
 * Represents an abstract monster entity in the game.
 * Manages monster attributes and behaviors.
 */
public abstract class Monster extends Movable {

    /** The player target that the monster interacts with. */
    protected Player target;
    /** Current health points of the monster. */
    protected float hp;
    /** Damage inflicted by the monster. */
    protected float damages;
    /** Experience points value awarded when the monster is defeated. */
    private int xpValue;

    /**
     * Constructs a new Monster with specified attributes.
     *
     * @param texture The texture of the monster.
     * @param posX    The X position.
     * @param posY    The Y position.
     * @param sizeX   The width size.
     * @param sizeY   The height size.
     * @param speed   The movement speed.
     * @param hp      The health points.
     * @param damages The damage dealt.
     * @param xpValue The experience points value upon defeat.
     */
    public Monster(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed, float hp,
            float damages, int xpValue) {
        super(texture, posX, posY, sizeX, sizeY, speed);
        this.hp = hp;
        this.damages = damages;
        this.xpValue = xpValue;
    }

    /**
     * Checks if the monster is dead and handles death logic.
     */
    public void isDead() {
        if (this.hp <= 0) {
            entities.remove(this);
            this.dispose();
            dropXp();
        }
    }

    /**
     * Sets the monster's health points and checks for death.
     *
     * @param newHp The new health points value.
     */
    public void setHp(int newHp) {
        this.hp = newHp;
        isDead();
    }

    /**
     * Gets the monster's current health points.
     *
     * @return The current health points.
     */
    public float getHp() {
        return this.hp;
    }

    /**
     * Gets the damage value of the monster.
     *
     * @return The damage inflicted by the monster.
     */
    public float getDamages() {
        return this.damages;
    }

    /**
     * Applies damage to the monster and checks for death.
     *
     * @param damage The amount of damage to apply.
     */
    public void takeDamage(float damage) {
        this.hp -= damage;
        isDead();
    }

    /**
     * Drops experience points upon the monster's death.
     */
    public void dropXp() {
        new Xp(this.getPosX(), this.getPosY(), this.xpValue);
    }
}
