package io.github.spaceSurvivor.dropable;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Player;

public class HealBuff extends Entity {

    private float healValue;

    public HealBuff(float healValue, float posX, float posY) {
        super(new Texture("Dropable/healBuff.png"), posX, posY, 20, 20);
        this.healValue = healValue;
    }

    public void applyHealBuff(Player player) {
        player.setHp(player.getHp() + (player.getMaxHp() * healValue));
    }

    public float getHealValue() {
        return this.healValue;
    }
}
