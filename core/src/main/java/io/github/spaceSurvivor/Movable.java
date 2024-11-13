package io.github.spaceSurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Movable extends Entity {

    protected float speed;

    public Movable(Texture texture, float posX, float posY, int sizeX, int sizeY, float speed) {
        super(texture, posX, posY, sizeX, sizeY);
        this.speed = speed;
    }

    public void move(Player target) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        float directionX = target.getPosX() - this.getPosX();
        float directionY = target.getPosY() - this.getPosY();

        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);

        if (length != 0) { // norm
            directionX /= length;
            directionY /= length;
        }

        this.setPosX(this.getPosX() + directionX * this.getSpeed() * deltaTime);
        this.setPosY(this.getPosY() + directionY * this.getSpeed() * deltaTime);
    }

    public float getSpeed() {
        return this.speed;
    }
}
