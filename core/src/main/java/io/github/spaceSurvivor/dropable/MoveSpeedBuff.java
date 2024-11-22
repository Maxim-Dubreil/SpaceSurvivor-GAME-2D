package io.github.spaceSurvivor.dropable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Player;

public class MoveSpeedBuff extends Entity {

    private int moveSPeedBuff;

    public MoveSpeedBuff(int moveSPeedBuff, float posX, float posY) {
        super(new Texture("Dropable/moveSpeedBuff.png"), posX, posY, 20, 20);
        this.moveSPeedBuff = moveSPeedBuff;
    }

    public void applyMoveSpeedBuff(Player player) {
        player.setSpeed((player.getSpeed() / Map.getUnitScale()) + this.moveSPeedBuff);
        Timer.schedule(new Task() {
            @Override
            public void run() {
                player.setSpeed((player.getSpeed() / Map.getUnitScale()) - moveSPeedBuff);
            }
        }, 10);
    }

    public int getMoveSpeedValue() {
        return this.moveSPeedBuff;
    }
}
