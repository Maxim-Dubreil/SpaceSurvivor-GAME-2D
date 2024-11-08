package io.github.spaceSurvivor;

public class Monster extends Movable {

    protected Player target;

    public Monster(int posX, int posY, int sizeX, int sizeY, float speed) {
        super(posX, posY, sizeX, sizeY, new float[] { 0, 1, 1, 0 }, speed);
    }

}
