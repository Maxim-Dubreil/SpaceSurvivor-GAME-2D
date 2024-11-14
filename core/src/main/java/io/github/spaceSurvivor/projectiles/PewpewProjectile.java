package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Player;

public class PewpewProjectile extends Projectile {
    public PewpewProjectile(float posX, float posY) {
        super(new Texture("Projectile/pewpewbullet.png"), posX * Map.getTileSize(), posY * Map.getTileSize(), 50, 50,
                150);
        System.err.println("Constructor pos : " + posX + " || " + posY);
    }

    @Override
    public void move(Player player) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.setPosY(this.getPosY() + this.getSpeed() * deltaTime);
        if (this.getPosY() > 100 || this.getPosY() < -100 || this.getPosX() > 100 || this.getPosX() < -100) {
            Entity.entities.remove(this);
            System.out.println("ENTITY DELETED OUT OF RANGE");
            this.dispose();
        }
    }
}
