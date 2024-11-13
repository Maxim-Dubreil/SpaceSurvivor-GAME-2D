package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Movable;

public abstract class Projectile extends Movable {

    public Projectile(Texture texture, float posX, float posY, int sizeX, int sizeY, float speed) {
        super(texture, posX, posY, sizeX, sizeY, speed);
    }

    public abstract void update();
}
