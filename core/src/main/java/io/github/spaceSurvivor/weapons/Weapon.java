// Weapon.java

package io.github.spaceSurvivor.weapons;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import io.github.spaceSurvivor.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Weapon {

    private int damages;
    private int range;
    private boolean isPhantom;
    private float rate;
    private Task shootingTask;
    protected Player player;

    public static List<Weapon> weapons = new ArrayList<Weapon>();

    public Weapon(Player player, int damages, int range, boolean isPhantom, float rate) {
        this.damages = damages;
        this.range = range;
        this.isPhantom = isPhantom;
        this.rate = rate;
        weapons.add(this);
        startShooting(player);
    }

    public void setRate(float newRate) {
        this.rate = newRate;
    }

    // ====================== GETTERS ======================

    public int getDamages() {
        return this.damages;
    }

    public int getRange() {
        return this.range;
    }

    public boolean isPhantom() {
        return this.isPhantom;
    }

    public float getRate() {
        return this.rate;
    }

    public abstract void shotProjectile(Player player);

    public void destroy() {
        this.destroy();
    }

    public void startShooting(Player player) {
        shootingTask = new Task() {
            @Override
            public void run() {
                shotProjectile(player);
            }
        };
        Timer.schedule(shootingTask, 0, this.rate);
    }

    public void stopShooting() {
        if (shootingTask != null) {
            shootingTask.cancel();
            shootingTask = null;
        }
    }
}
