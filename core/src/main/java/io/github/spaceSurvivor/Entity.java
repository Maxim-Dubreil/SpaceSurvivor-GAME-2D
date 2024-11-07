package io.github.spaceSurvivor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Entity {
    protected ShapeRenderer shapeRenderer;
    protected float posX;
    protected float posY;
    protected int sizeX;
    protected int sizeY;
    protected float[] color;

    public Entity(int posX, int posY, int sizeX, int sizeY, float[] color) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.color = color;
        createShape();
    }

    public void createShape() {
        shapeRenderer = new ShapeRenderer();
    }

    public void renderShape() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(this.color[0], this.color[1], this.color[2], this.color[3]);
        shapeRenderer.rect(this.posX, this.posY, this.sizeX, this.sizeY);
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
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

    public float[] getColor() {
        return this.color;
    }

    public int getSquareSize() {
        return (sizeX + sizeY) / 2;
    }
}
