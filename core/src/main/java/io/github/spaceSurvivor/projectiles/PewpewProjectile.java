package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.weapons.Weapon;

public class PewpewProjectile extends Projectile {
    public PewpewProjectile(float posX, float posY, float[] direction, Weapon weapon) {
        super(new Texture("Projectile/pewpewbullet.png"), posX * Map.getTileSize(), posY * Map.getTileSize(), 30, 30,
                300, weapon, direction);
    }

}
