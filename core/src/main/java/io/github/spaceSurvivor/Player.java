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

    public Player() {
        super(new Texture("Player/player1.png"), 100, 100, 50, 50, 150);
        Player.weapons.add(new Pewpew(this));
    }

    public void move() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.setPosX(this.getPosX() - this.getSpeed() * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.setPosX(this.getPosX() + this.getSpeed() * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            this.setPosY(this.getPosY() + this.getSpeed() * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            this.setPosY(this.getPosY() - this.getSpeed() * deltaTime);
        }

        // this.setPosX(Math.max(0, Math.min(this.getPosX(), 1920 -
        // this.getHitBox())));
        // this.setPosY(Math.max(0, Math.min(this.getPosY(), 1080 -
        // this.getHitBox())));
    }

    // public void attack() {
    // for (Weapon weapon : weapons) {
    // weapon.shot();
    // }
    // }

}
