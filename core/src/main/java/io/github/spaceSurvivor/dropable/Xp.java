package io.github.spaceSurvivor.dropable;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Map;

public class Xp extends Entity {

    public Xp(float posX, float posY) {
        super(new Texture("Dropable/XpAsset.png"), posX * Map.getTileSize() - 2, posY * Map.getTileSize() - 2, 20, 20);
    }
}
