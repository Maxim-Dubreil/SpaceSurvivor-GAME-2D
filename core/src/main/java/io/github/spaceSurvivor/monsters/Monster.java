package io.github.spaceSurvivor.monsters;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.Player;

public abstract class Monster extends Movable {

    protected Player target;

    public Monster(Texture texture, int posX, int posY, int sizeX, int sizeY, float speed) {
        super(texture, posX, posY, sizeX, sizeY, speed);
    }

}