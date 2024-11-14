package io.github.spaceSurvivor.monsters;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.Player;

public abstract class Monster extends Movable {

    protected Player target;

    public Monster(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed) {
        super(texture, posX, posY, sizeX, sizeY, speed);
    }

}