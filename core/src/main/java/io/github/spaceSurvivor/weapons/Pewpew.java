package io.github.spaceSurvivor.weapons;

import com.badlogic.gdx.utils.Timer;

import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.projectiles.PewpewProjectile;

public class Pewpew extends Weapon {
    private Player player;

    public Pewpew(Player player) {
        super(player, 10, 80, false, 0.5f);
        this.player = player;

    }

    @Override
    public void shotProjectile(Player player) {
        System.out.println("pewpew :" + this.getDamages() + " damages");
        System.out.println("PLayer pos : " + player.getPosX() + " " + player.getPosY());

        PewpewProjectile projectile = new PewpewProjectile(player.getPosX(), player.getPosY());
    }

    public void stopShooting() {
        Timer.instance().clear();
    }
}
