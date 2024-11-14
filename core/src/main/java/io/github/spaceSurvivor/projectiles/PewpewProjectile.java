package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Map;

public class PewpewProjectile extends Projectile {
    public PewpewProjectile(float posX, float posY, float[] direction) {
        super(new Texture("Projectile/pewpewbullet.png"), posX * Map.getTileSize(), posY * Map.getTileSize(), 30, 30,
                300, direction);
    }

}
