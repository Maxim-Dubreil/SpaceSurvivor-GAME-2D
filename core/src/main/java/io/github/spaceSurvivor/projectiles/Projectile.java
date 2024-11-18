package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Movable;

import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.Map;


import io.github.spaceSurvivor.weapons.Weapon;


public abstract class Projectile extends Movable {

    private float directionX;
    private float directionY;
    private Weapon weapon;

    public Projectile(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed, Weapon weapon,
            float[] direction) {
        super(texture, posX, posY, sizeX, sizeY, speed);
        this.directionX = direction[0];
        this.directionY = direction[1];
        this.weapon = weapon;
        System.out.println("Entities == " + Entity.entities);
    }
    //vérifier les projectiles réduire le nombre
    public void move(CollisionManager collisionManager, Map map) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.setPosX(this.getPosX() + this.directionX * this.getSpeed() * deltaTime);
        this.setPosY(this.getPosY() + this.directionY * this.getSpeed() * deltaTime);

        if (this.getPosY() > 100 || this.getPosY() < -100 || this.getPosX() > 100 || this.getPosX() < -100) {
            Entity.entities.remove(this);
            this.dispose();
        }else if (collisionManager.handleEntityMapCollision(this, map)) {
            Entity.entities.remove(this);
            this.dispose();
        }
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public float getDamage() {
        return this.weapon.getDamages();
    }
}
