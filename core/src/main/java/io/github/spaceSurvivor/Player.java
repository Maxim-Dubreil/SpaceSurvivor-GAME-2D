package io.github.spaceSurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Movable {

    public Player(int posX, int posY, int sizeX, int sizeY, float speed) {
        super(posX, posY, sizeX, sizeY, new float[] { 1, 0, 1, 0 }, speed);
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

        this.setPosX(Math.max(0, Math.min(this.getPosX(), 1920 -
                this.getSquareSize())));
        this.setPosY(Math.max(0, Math.min(this.getPosY(), 1080 -
                this.getSquareSize())));
    }

}
