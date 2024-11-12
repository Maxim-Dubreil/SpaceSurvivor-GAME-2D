package io.github.spaceSurvivor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {

    protected Sprite sprite;
    protected SpriteBatch batch;
    protected Texture texture;

    protected float posX;
    protected float posY;
    protected int sizeX;
    protected int sizeY;

    public static List<Entity> entities = new ArrayList<>();

    public Entity(Texture texture, int posX, int posY, int sizeX, int sizeY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.texture = texture;
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
        this.sprite.getTexture().dispose();
    }

    // ====================== SETTERS ======================
    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    // ====================== GETTERS ======================

    public Rectangle getBoundingBox() {
        return new Rectangle(posX, posY, sizeX, sizeY);
    }

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

    public int getHitbox() {
        return (sizeX + sizeY) / 2;
    }
}
