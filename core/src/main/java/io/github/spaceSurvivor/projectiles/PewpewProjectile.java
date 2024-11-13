package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;

public class PewpewProjectile extends Projectile {
    public PewpewProjectile(float posX, float posY) {
        super(new Texture("Projectile/pewpewbullet.png"), posX, posY, 50, 50, 150);
    }

    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.setPosY(this.getPosY() + this.getSpeed() * deltaTime);
        if (this.getPosY() > 2000 || this.getPosX() > 2000) {
            Entity.entities.remove(this);
            this.dispose();
        }
    }
}
