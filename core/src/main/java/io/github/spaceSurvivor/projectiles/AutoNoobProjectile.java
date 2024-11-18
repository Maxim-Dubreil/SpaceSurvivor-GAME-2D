package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.weapons.Weapon;

public class AutoNoobProjectile extends Projectile {
    public AutoNoobProjectile(float posX, float posY, float[] direction, Weapon weapon) {
        super(new Texture("Projectile/autoNoob-projectile.png"), posX * Map.getTileSize(), posY * Map.getTileSize(), 30,
                30,
                700, weapon, direction);
    }

}
