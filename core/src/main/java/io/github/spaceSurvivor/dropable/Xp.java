package io.github.spaceSurvivor.dropable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.Player;

public class Xp extends Movable {

    private static final float ATTRACT_DISTANCE = 100f;
    private Player player;
    private int xpValue;

    public Xp(float posX, float posY, int xpValue) {
        super(new Texture("Dropable/XpAsset.png"), posX * Map.getTileSize() - 2, posY * Map.getTileSize() - 2, 20, 20,
                100);
        this.xpValue = xpValue;
    }

    public void getAttracted(Player player) {
        this.player = player;

        float distance = Vector2.dst(this.getPosX(), this.getPosY(), player.getPosX(), player.getPosY());

        if (distance < ATTRACT_DISTANCE) {

            Vector2 direction = new Vector2(player.getPosX() - this.getPosX(), player.getPosY() - this.getPosY()).nor();
            float deltaTime = Gdx.graphics.getDeltaTime();
            this.setPosX(this.getPosX() + direction.x * this.getSpeed() * deltaTime);
            this.setPosY(this.getPosY() + direction.y * this.getSpeed() * deltaTime);
            Entity.entities.remove(this);
            this.dispose();
        }
    }

    public int getXpValue() {
        return this.xpValue;
    }
}
