package io.github.spaceSurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.github.spaceSurvivor.managers.CollisionManager;

public abstract class Movable extends Entity {

    protected float speed;
    protected float directionX;
    protected float directionY;

    public Movable(Texture texture, float posX, float posY, float sizeX, float sizeY, float speed) {
        super(texture, posX, posY, sizeX, sizeY);
        this.speed = speed * Map.getUnitScale();
    }

    public void move(Player target, CollisionManager collisionManager, Map map) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float oldX = this.getPosX();
        float oldY = this.getPosY();

        directionX = target.getPosX() - this.getPosX();
        directionY = target.getPosY() - this.getPosY();

        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);

        if (length != 0) { // norm
            directionX /= length;
            directionY /= length;
        }

        // Essayez d'abord de bouger en X
        this.setPosX(this.getPosX() + directionX * this.getSpeed() * deltaTime);
        if (collisionManager.handleEntityMapCollision(this, map)) {
            this.setPosX(oldX);  // Revenir en arrière si collision en X
        }

        // Ensuite, essayez de bouger en Y
        this.setPosY(this.getPosY() + directionY * this.getSpeed() * deltaTime);
        if (collisionManager.handleEntityMapCollision(this, map)) {
            this.setPosY(oldY);  // Revenir en arrière si collision en Y
        }

        // Si aucun mouvement n'a été possible, essayez de vous déplacer diagonalement
        if (this.getPosX() == oldX && this.getPosY() == oldY) {
            float diagonalX = oldX + directionX * this.getSpeed() * deltaTime * 0.7f;
            float diagonalY = oldY + directionY * this.getSpeed() * deltaTime * 0.7f;

            this.setPosX(diagonalX);
            this.setPosY(diagonalY);

            if (collisionManager.handleEntityMapCollision(this, map)) {
                this.setPosX(oldX);
                this.setPosY(oldY);
            }
        }
    }

    public float getSpeed() {
        return this.speed;
    }

    public float[] getDirection() {
        return new float[] { directionX, directionY };
    }
}
