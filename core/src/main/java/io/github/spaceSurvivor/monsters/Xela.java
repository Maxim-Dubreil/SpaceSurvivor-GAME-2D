package io.github.spaceSurvivor.monsters;

import com.badlogic.gdx.graphics.Texture;

public class Xela extends Monster {
    public Xela(float posX, float posY) {
        super(new Texture("Monster/xelaAsset.png"), posX, posY, 32, 32, 40, 50, 10, 10);
    }
}
