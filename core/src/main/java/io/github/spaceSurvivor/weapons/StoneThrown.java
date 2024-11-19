package io.github.spaceSurvivor.weapons;

import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.projectiles.StoneThrownProjectile;

public class StoneThrown extends Weapon {

    public StoneThrown(Player player) {
        super(player, 80, 5, false, 0.8f);
    }

    @Override
    public void shotProjectile(Player player) {
        if (StoneThrownProjectile.isEnemyNearby(player.getPosX(), player.getPosY(), this.getRange())) {
            new StoneThrownProjectile((player.getPosX() + 1.6f), (player.getPosY() + 1f), player.getDirection(), this,
                    this.getRange());
        }
    }
}
