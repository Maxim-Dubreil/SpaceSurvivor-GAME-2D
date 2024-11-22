// Pewpew.java

package io.github.spaceSurvivor.weapons;

import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.projectiles.PewpewProjectile;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Pewpew extends Weapon {

    private OrthographicCamera camera;

    public Pewpew(Player player) {
        super(player, 80, 80, false, 0.5f);
        this.camera = Map.camera;
    }

    @Override
    public void shotProjectile(Player player) {
        new PewpewProjectile((Player.posX + 1.6f), (Player.posY + 1f), this, this.camera);

    }
}
