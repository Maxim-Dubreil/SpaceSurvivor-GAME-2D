package io.github.spaceSurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.github.spaceSurvivor.managers.CollisionManager;

/**
 * Represents a movable entity in the game.
 * Extends the Entity class with movement capabilities.
 */
public abstract class Movable extends Entity {

    /** Movement speed of the entity. */
    protected float speed;
    /** X component of the movement direction. */
    protected float directionX;
    /** Y component of the movement direction. */
    protected float directionY;

    /**
     * Constructs a new Movable entity.
     *
     * @param texture The texture of the entity.
     * @param posX    The X position.
     * @param posY    The Y position.
     * @param sizeX   The width size.
     * @param sizeY   The height size.
     * @param speed   The movement speed.
     */
    public Movable(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed) {
        super(texture, posX, posY, sizeX, sizeY);
        this.speed = speed * Map.getUnitScale();
    }

    /**
     * Moves the entity towards the target while handling collisions.
     *
     * @param target           The player target.
     * @param collisionManager The collision manager.
     * @param map              The game map.
     */
    public void move(Player target, CollisionManager collisionManager, Map map) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float oldX = this.getPosX();
        float oldY = this.getPosY();

        directionX = target.getPosX() - this.getPosX();
        directionY = target.getPosY() - this.getPosY();

        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);

        if (length != 0) { // Normalize
            directionX /= length;
            directionY /= length;
        }

        // Try moving in X direction first
        this.setPosX(this.getPosX() + directionX * this.getSpeed() * deltaTime);
        if (collisionManager.handleEntityMapCollision(this, map)) {
            this.setPosX(oldX); // Revert if collision in X
        }

        // Then try moving in Y direction
        this.setPosY(this.getPosY() + directionY * this.getSpeed() * deltaTime);
        if (collisionManager.handleEntityMapCollision(this, map)) {
            this.setPosY(oldY); // Revert if collision in Y
        }

        // If no movement was possible, try moving diagonally
        if (this.getPosX() == oldX && this.getPosY() == oldY) {
            float diagonalX = oldX + directionX * this.getSpeed() * deltaTime * 0.7f;
            float diagonalY = oldY + directionY * this.getSpeed() * deltaTime * 0.7f;

            this.setPosX(diagonalX);
            this.setPosY(diagonalY);

            if (collisionManager.handleEntityMapCollision(this, map)) {
                this.setPosX(oldX);
                this.setPosY(oldY);
            }
        }
    }

    /**
     * Gets the movement speed of the entity.
     *
     * @return The movement speed.
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Gets the current movement direction of the entity.
     *
     * @return An array containing the X and Y components of the direction.
     */
    public float[] getDirection() {
        return new float[] { directionX, directionY };
    }

    /**
     * Sets the movement speed of the entity.
     *
     * @param newSpeed The new speed value.
     */
    public void setSpeed(float newSpeed) {
        this.speed = newSpeed * Map.getUnitScale();
    }
}
