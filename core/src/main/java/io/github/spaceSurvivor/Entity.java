package io.github.spaceSurvivor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {

    public static List<Entity> entities = new ArrayList<>();

    protected float posX;
    protected float posY;
    protected int sizeX;
    protected int sizeY;
    protected Texture texture;
    protected SpriteBatch batch;

    protected com.badlogic.gdx.graphics.g2d.Sprite sprite;

    public Entity(Texture texture, float posX, float posY, int sizeX, int sizeY) {
        this.texture = texture;
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture);
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

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
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
