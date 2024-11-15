package io.github.spaceSurvivor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.weapons.Pewpew;
import io.github.spaceSurvivor.weapons.Weapon;

public class Player extends Movable {

    public static List<Weapon> weapons = new ArrayList<>();
    protected boolean isDead = false;
    private float lastDirectionX = 0;
    private float lastDirectionY = 1;
    private float hp = 100;

    public Player() {
        super(new Texture("Player/player1.png"), 100, 100, 50, 50, 150);
        Player.weapons.add(new Pewpew(this));
    }

    public void move() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean moved = false;

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.setPosX(this.getPosX() - this.getSpeed() * deltaTime);
            lastDirectionX = -1;
            lastDirectionY = 0;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.setPosX(this.getPosX() + this.getSpeed() * deltaTime);
            lastDirectionX = 1;
            lastDirectionY = 0;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            this.setPosY(this.getPosY() + this.getSpeed() * deltaTime);
            lastDirectionX = 0;
            lastDirectionY = 1;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            this.setPosY(this.getPosY() - this.getSpeed() * deltaTime);
            lastDirectionX = 0;
            lastDirectionY = -1;
            moved = true;
        }

        if (moved) {
            float length = (float) Math.sqrt(lastDirectionX * lastDirectionX + lastDirectionY * lastDirectionY);
            if (length != 0) {
                lastDirectionX /= length;
                lastDirectionY /= length;
            }
        }
    }

    public void isDead() {
        if (this.hp <= 0) {
            entities.remove(this);
            this.dispose();
            this.isDead = true;
        }
    }

    public float[] getDirection() {
        return new float[] { lastDirectionX, lastDirectionY };
    }

    public void takeDamage(float damage) {
        this.hp -= damage;
        isDead();
    }

    public void setHp(int newHp) {
        this.hp = newHp;
    }

    public float getHp() {
        return this.hp;
    }
}
