package io.github.spaceSurvivor.monsters;

import com.badlogic.gdx.graphics.Texture;

public class Trouille extends Monster {

    public Trouille(float posX, float posY) {
        super(new Texture("Monster/trouilleAsset.png"), posX, posY, 32, 32, 20, 100, 20, 20);
    }
}
