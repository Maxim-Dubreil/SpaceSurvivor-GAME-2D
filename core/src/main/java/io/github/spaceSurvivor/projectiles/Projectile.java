package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Movable;

public abstract class Projectile extends Movable {

    private float directionX;
    private float directionY;

    public Projectile(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed,
            float[] direction) {
        super(texture, posX, posY, sizeX, sizeY, speed);
        this.directionX = direction[0];
        this.directionY = direction[1];
    }

    public void move() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.setPosX(this.getPosX() + this.directionX * this.getSpeed() * deltaTime);
        this.setPosY(this.getPosY() + this.directionY * this.getSpeed() * deltaTime);

        if (this.getPosY() > 100 || this.getPosY() < -100 || this.getPosX() > 100 || this.getPosX() < -100) {
            Entity.entities.remove(this);
            this.dispose();
        }
    }
}
