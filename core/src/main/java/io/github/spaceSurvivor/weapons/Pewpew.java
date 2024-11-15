package io.github.spaceSurvivor.weapons;

import com.badlogic.gdx.utils.Timer;

import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.projectiles.PewpewProjectile;

public class Pewpew extends Weapon {
    private Player player;

    public Pewpew(Player player) {
        super(player, 80, 80, false, 0.5f);
        this.player = player;

    }

    @Override
    public void shotProjectile(Player player) {
        PewpewProjectile projectile = new PewpewProjectile(player.getPosX(), player.getPosY(),
                player.getDirection(), this);
    }

    public void stopShooting() {
        Timer.instance().clear();
    }
}
