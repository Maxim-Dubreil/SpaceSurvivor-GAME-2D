package io.github.spaceSurvivor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import io.github.spaceSurvivor.monsters.Monster;

/**
 * Represents a generic entity in the game.
 * Manages basic attributes like position, size, and rendering.
 */
public abstract class Entity {

    /** List of all entities in the game. */
    public static List<Entity> entities = new ArrayList<>();

    /** X position of the entity. */
    protected float posX;
    /** Y position of the entity. */
    protected float posY;
    /** Width of the entity. */
    protected float sizeX;
    /** Height of the entity. */
    protected float sizeY;
    /** Texture of the entity. */
    protected Texture texture;
    /** Sprite batch for rendering. */
    protected SpriteBatch batch;

    /** Sprite representation of the entity. */
    protected Sprite sprite;

    /**
     * Constructs a new Entity with specified attributes.
     *
     * @param texture The texture of the entity.
     * @param posX    The X position.
     * @param posY    The Y position.
     * @param sizeX   The width size.
     * @param sizeY   The height size.
     */
    public Entity(Texture texture, float posX, float posY, float sizeX, float sizeY) {
        this.texture = texture;
        this.posX = posX * Map.getUnitScale();
        this.posY = posY * Map.getUnitScale();
        this.sizeX = sizeX * Map.getUnitScale();
        this.sizeY = sizeY * Map.getUnitScale();

        this.sprite = new Sprite(texture);
        this.sprite.setSize(this.sizeX, this.sizeY);
        this.sprite.setPosition(this.posX, this.posY);

        entities.add(this);
    }

    /**
     * Renders the entity using the provided sprite batch.
     *
     * @param batch The sprite batch used for rendering.
     */
    public void renderEntity(SpriteBatch batch) {

        this.sprite.setPosition(this.posX, this.posY);
        this.sprite.draw(batch);
    }

    /**
     * Disposes of the entity's resources and removes it from the entity list.
     */
    public void dispose() {
        this.texture.dispose();
        entities.remove(this);
    }

    /**
     * Checks if the entity is an enemy (instance of Monster).
     *
     * @return True if the entity is a Monster, false otherwise.
     */
    public boolean isEnemy() {
        return this instanceof Monster;
    }

    // ====================== SETTERS ======================

    /**
     * Sets the entity's X position.
     *
     * @param posX The new X position.
     */
    public void setPosX(float posX) {
        this.posX = posX;
    }

    /**
     * Sets the entity's Y position.
     *
     * @param posY The new Y position.
     */
    public void setPosY(float posY) {
        this.posY = posY;
    }

    // ====================== GETTERS ======================

    /**
     * Gets the entity's X position.
     *
     * @return The X position.
     */
    public float getPosX() {
        return this.posX;
    }

    /**
     * Gets the entity's Y position.
     *
     * @return The Y position.
     */
    public float getPosY() {
        return this.posY;
    }

    /**
     * Gets the entity's width size.
     *
     * @return The width size.
     */
    public float getSizeX() {
        return this.sizeX;
    }

    /**
     * Gets the entity's height size.
     *
     * @return The height size.
     */
    public float getSizeY() {
        return this.sizeY;
    }

    /**
     * Gets the entity's texture.
     *
     * @return The texture.
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Gets the entity's hitbox as a Rectangle.
     *
     * @return The hitbox rectangle.
     */
    public Rectangle getHitBox() {
        return new Rectangle(posX, posY, sizeX, sizeY);
    }

    /**
     * Draws the entity's hitbox for debugging purposes.
     *
     * @param shapeRenderer The shape renderer used to draw the hitbox.
     */
    public void drawHitbox(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(posX, posY, sizeX, sizeY);
    }
}
