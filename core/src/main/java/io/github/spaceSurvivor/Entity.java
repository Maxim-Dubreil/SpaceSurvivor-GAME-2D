package io.github.spaceSurvivor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {

    public static List<Entity> entities = new ArrayList<>();

    protected float posX;
    protected float posY;
    protected float sizeX;
    protected float sizeY;
    protected Texture texture;
    protected SpriteBatch batch;

    protected Sprite sprite;

    public Entity(Texture texture, float posX, float posY, float sizeX, float sizeY) {
        this.texture = texture;
        this.posX = posX * Map.getUnitScale();
        this.posY = posY * Map.getUnitScale();
        this.sizeX = sizeX * Map.getUnitScale();
        this.sizeY = sizeY * Map.getUnitScale();


        this.sprite = new Sprite(texture);
        this.sprite.setSize(sizeX, sizeY);
        this.sprite.setPosition(posX, posY);

        entities.add(this);
    }

    public void renderEntity(SpriteBatch batch) {
        this.sprite.setPosition(this.posX, this.posY);
        this.sprite.draw(batch);
    }

    public void dispose() {
        this.texture.dispose();
    }

    // ====================== SETTERS ======================

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    // ====================== GETTERS ======================

    public float getPosX() {
        return this.posX;
    }

    public float getPosY() {
        return this.posY;
    }

    public float getSizeX() {
        return this.sizeX;
    }

    public float getSizeY() {
        return this.sizeY;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Rectangle getHitBox() {
        return new Rectangle(posX, posY, sizeX, sizeY);
    }

    // public int getHitbox() {
    // return (sizeX + sizeY) / 2;
    // }
}
