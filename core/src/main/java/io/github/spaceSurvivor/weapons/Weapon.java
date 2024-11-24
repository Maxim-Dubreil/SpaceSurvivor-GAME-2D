package io.github.spaceSurvivor.weapons;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import io.github.spaceSurvivor.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a weapon that can fire projectiles.
 */
public abstract class Weapon {

    /** The damage dealt by the weapon's projectiles. */
    private int damages;

    /** The range of the weapon. */
    private int range;

    /** Indicates if the weapon's projectiles can pass through entities. */
    private boolean isPhantom;

    /** The rate of fire (seconds between shots). */
    private float rate;

    /** The task responsible for automatic shooting. */
    private Task shootingTask;

    /** The player who owns the weapon. */
    protected Player player;

    /** List of all weapons in the game. */
    public static List<Weapon> weapons = new ArrayList<>();

    /**
     * Constructs a new Weapon.
     *
     * @param player    The player who owns the weapon.
     * @param damages   The damage dealt by the weapon.
     * @param range     The range of the weapon.
     * @param isPhantom Whether the weapon's projectiles are phantom.
     * @param rate      The rate of fire.
     */
    public Weapon(Player player, int damages, int range, boolean isPhantom, float rate) {
        this.damages = damages;
        this.range = range;
        this.isPhantom = isPhantom;
        this.rate = rate;
        weapons.add(this);
        startShooting(player);
    }

    /**
     * Sets the rate of fire for the weapon.
     *
     * @param newRate The new rate of fire.
     */
    public void setRate(float newRate) {
        this.rate = newRate;
    }

    // ====================== GETTERS ======================

    /**
     * Gets the damage dealt by the weapon.
     *
     * @return The damage value.
     */
    public int getDamages() {
        return this.damages;
    }

    /**
     * Gets the range of the weapon.
     *
     * @return The range value.
     */
    public int getRange() {
        return this.range;
    }

    /**
     * Checks if the weapon's projectiles are phantom.
     *
     * @return True if phantom, false otherwise.
     */
    public boolean isPhantom() {
        return this.isPhantom;
    }

    /**
     * Gets the rate of fire.
     *
     * @return The rate of fire.
     */
    public float getRate() {
        return this.rate;
    }

    /**
     * Abstract method to shoot a projectile.
     *
     * @param player The player who owns the weapon.
     */
    public abstract void shotProjectile(Player player);

    /**
     * Destroys the weapon and removes it from the game.
     */
    public void destroy() {
        weapons.remove(this);
        stopShooting();
    }

    /**
     * Starts the automatic shooting of the weapon.
     *
     * @param player The player who owns the weapon.
     */
    public void startShooting(Player player) {
        shootingTask = new Task() {
            @Override
            public void run() {
                shotProjectile(player);
            }
        };
        Timer.schedule(shootingTask, 0, this.rate);
    }

    /**
     * Stops the automatic shooting of the weapon.
     */
    public void stopShooting() {
        if (shootingTask != null) {
            shootingTask.cancel();
            shootingTask = null;
        }
    }
}
