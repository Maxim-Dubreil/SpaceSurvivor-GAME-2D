// PewpewProjectile.java

package io.github.spaceSurvivor.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.weapons.Weapon;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PewpewProjectile extends Projectile {

    public PewpewProjectile(float posX, float posY, Weapon weapon, OrthographicCamera camera) {
        super(new Texture("Projectile/pewpewbullet.png"), posX * Map.getTileSize(), posY * Map.getTileSize(), 30, 30,
                500, weapon, new float[] { 0, 0 });

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        Vector3 worldCoords = camera.unproject(new Vector3(mouseX, mouseY, 0));

        float projectileX = this.getPosX();
        float projectileY = this.getPosY();

        Vector2 directionVector = new Vector2(worldCoords.x - projectileX, worldCoords.y - projectileY).nor();

        this.directionX = directionVector.x;
        this.directionY = directionVector.y;
    }
}
