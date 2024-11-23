package io.github.spaceSurvivor.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.projectiles.BossProjectiles;

public class Boss extends Monster {
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private TextureRegion currentFrame;
    private float stateTime = 0f;
    private Timer.Task shootingTask;
    private float shootingRate = 1.0f;

    public Boss(float posX, float posY, Player player) {
        super(new Texture("Monster/GolemSprite.png"), posX, posY, 120, 120, 60, 10000, 20, 100);
        loadAnimations(new Texture("Monster/GolemSprite.png"));
        startShooting(player);
    }

    private void loadAnimations(Texture spriteSheet) {
        int frameWidth = 64; // Adjust based on your sprite sheet
        int frameHeight = 64; // Adjust based on your sprite sheet

        TextureRegion[][] frames = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        Array<TextureRegion> walkRightFrames = new Array<>();
        Array<TextureRegion> walkLeftFrames = new Array<>();
        Array<TextureRegion> walkDownFrames = new Array<>();

        // Assuming the right walk animation is on the second row (index 1)
        for (int i = 0; i < 4; i++) { // Adjust the number of frames based on your sprite sheet
            walkRightFrames.add(frames[1][i]);
        }

        // Assuming the left walk animation is on the fourth row (index 3)
        for (int i = 0; i < 4; i++) { // Adjust the number of frames based on your sprite sheet
            walkLeftFrames.add(frames[3][i]);
        }

        for (int i = 0; i < 4; i++) {
            walkDownFrames.add(frames[2][i]);
        }

        walkRightAnimation = new Animation<>(0.2f, walkRightFrames);
        walkLeftAnimation = new Animation<>(0.2f, walkLeftFrames);
        walkDownAnimation = new Animation<>(0.2f, walkDownFrames);

        currentFrame = walkRightAnimation.getKeyFrame(0);
    }

    @Override
    public void move(Player target, CollisionManager collisionManager, Map map) {
        super.move(target, collisionManager, map);
        stateTime += Gdx.graphics.getDeltaTime();

        // Determine direction based on the target's position
        if (this.getPosX() < target.getPosX()) {
            // Moving right
            currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
        } else if (this.getPosX() == target.getPosX()) {
            currentFrame = walkDownAnimation.getKeyFrame(stateTime, true);
        } else {
            // Moving left
            currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
        }
    }

    public void shotProjectile(Player player) {
        new BossProjectiles((this.posX + 1.6f), (this.posY + 1f), this, player);
        System.out.println("Boss life: " + this.getHp());

    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void startShooting(Player player) {
        shootingTask = new Task() {
            @Override
            public void run() {
                shotProjectile(player);
            }
        };
        Timer.schedule(shootingTask, 0, shootingRate);
    }

    public void stopShooting() {
        if (shootingTask != null) {
            shootingTask.cancel();
            shootingTask = null;
        }
    }

    public Rectangle getHitBox() {
        float hitboxWidth = sizeX / 2;
        float hitboxHeight = sizeY / 2;
        float centerX = this.posX + sizeX / 3;
        float centerY = this.posY + sizeY / 3;
        return new Rectangle(centerX - hitboxWidth / 3, centerY - hitboxHeight / 3, hitboxWidth, hitboxHeight);
    }

}
