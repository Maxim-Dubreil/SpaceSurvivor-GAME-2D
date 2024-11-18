package io.github.spaceSurvivor.weapons;

import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.projectiles.AutoNoobProjectile;

public class AutoNoob extends Weapon {

    public AutoNoob(Player player) {
        super(player, 120, 80, false, 1f);

    }

    @Override
    public void shotProjectile(Player player) {
        new AutoNoobProjectile((player.getPosX() + 1.2f), (player.getPosY() + 1f),
                player.getDirection(), this);

    }

}
