package io.github.spaceSurvivor.monsters;

import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Movable;
import io.github.spaceSurvivor.Player;

public abstract class Monster extends Movable {

    protected Player target;
    protected float hp;
    protected float damages;

    public Monster(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed, float hp,
            float damages) {
        super(texture, posX, posY, sizeX, sizeY, speed);
        this.hp = hp;
        this.damages = damages;
    }

    public void isDead() {
        if (this.hp <= 0) {
            entities.remove(this);
            this.dispose();
        }
    }

    public void setHp(int newHp) {
        this.hp = newHp;
        isDead();
    }

    public float getHp() {
        return this.hp;
    }

    public float getDamages() {
        return this.damages;
    }

    public void takeDamage(float damage) {
        this.hp -= damage;
        isDead();
    }
}