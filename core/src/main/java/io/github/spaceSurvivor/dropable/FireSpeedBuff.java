package io.github.spaceSurvivor.dropable;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.weapons.Weapon;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class FireSpeedBuff extends Entity {

    private int fireSpeedValue;

    public FireSpeedBuff(int fireSpeedValue, float posX, float posY) {
        super(new Texture("Dropable/fireSpeedBuff.png"), posX, posY, 20, 20);
        this.fireSpeedValue = fireSpeedValue;
    }

    public void applyFireSpeedBuff(Player player) {
        for (Weapon weapon : Weapon.weapons) {
            System.out.println(weapon.getRate());

            weapon.setRate(weapon.getRate() / this.fireSpeedValue);
            weapon.stopShooting();
            weapon.startShooting(player);
            System.out.println("BUFF APPLIED");
            System.out.println(weapon.getRate());
        }

        Timer.schedule(new Task() {
            @Override
            public void run() {
                for (Weapon weapon : Weapon.weapons) {
                    weapon.setRate(weapon.getRate() * fireSpeedValue);
                    weapon.stopShooting();
                    weapon.startShooting(player);
                }
            }
        }, 10);
    }

    public int getFireSpeedValue() {
        return this.fireSpeedValue;
    }
}
