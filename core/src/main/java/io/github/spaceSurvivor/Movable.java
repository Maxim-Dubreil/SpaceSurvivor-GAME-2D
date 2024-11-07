package io.github.spaceSurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public abstract class Movable extends Entity {

    protected float speed;

    public Movable(int posX, int posY, int sizeX, int sizeY, float[] color, float speed) {
        super(posX, posY, sizeX, sizeY, color);
        this.speed = speed;
    }

    public void move() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.setPosX(this.getPosX() - this.getSpeed() * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.setPosX(this.getPosX() + this.getSpeed() * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            this.setPosY(this.getPosY() + this.getSpeed() * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            this.setPosY(this.getPosY() - this.getSpeed() * deltaTime);
        }

        this.setPosX(Math.max(0, Math.min(this.getPosX(), 800 -
                this.getSquareSize())));
        this.setPosY(Math.max(0, Math.min(this.getPosY(), 600 -
                this.getSquareSize())));
    }

    public float getSpeed() {
        return this.speed;
    }
}
