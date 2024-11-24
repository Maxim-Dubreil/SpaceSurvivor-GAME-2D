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

/**
 * Represents the Boss enemy in the game.
 * Inherits from the Monster class and includes unique behaviors and animations.
 */
public class Boss extends Monster {
    /** Animation for walking left. */
    private Animation<TextureRegion> walkLeftAnimation;
    /** Animation for walking right. */
    private Animation<TextureRegion> walkRightAnimation;
    /** Animation for walking down. */
    private Animation<TextureRegion> walkDownAnimation;
    /** The current frame being displayed. */
    private TextureRegion currentFrame;
    /** Tracks the time for animation state. */
    private float stateTime = 0f;
    /** Task responsible for the boss's shooting behavior. */
    private Timer.Task shootingTask;
    /** The rate at which the boss shoots projectiles (seconds between shots). */
    private float shootingRate = 1.0f;
    private boolean isDead;

    /**
     * Constructs a new Boss instance.
     *
     * @param posX   The initial X position.
     * @param posY   The initial Y position.
     * @param player The player instance to target.
     */
    public Boss(float posX, float posY, Player player) {
        super(new Texture("Monster/GolemSprite.png"), posX, posY, 120, 120, 30, 2000, 50, 1000);
        loadAnimations(new Texture("Monster/GolemSprite.png"));
        startShooting(player);
    }

    /**
     * Loads animations from the provided sprite sheet.
     *
     * @param spriteSheet The texture containing the sprite animations.
     */
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

        // Assuming the down walk animation is on the third row (index 2)
        for (int i = 0; i < 4; i++) { // Adjust the number of frames based on your sprite sheet
            walkDownFrames.add(frames[2][i]);
        }

        walkRightAnimation = new Animation<>(0.2f, walkRightFrames);
        walkLeftAnimation = new Animation<>(0.2f, walkLeftFrames);
        walkDownAnimation = new Animation<>(0.2f, walkDownFrames);

        currentFrame = walkRightAnimation.getKeyFrame(0);
    }

    /**
     * Updates the boss's movement and animation based on the player's position.
     *
     * @param target           The player to target.
     * @param collisionManager The collision manager for handling collisions.
     * @param map              The game map.
     */
    @Override
    public void move(Player target, CollisionManager collisionManager, Map map) {
        super.move(target, collisionManager, map);
        stateTime += Gdx.graphics.getDeltaTime();

        // Determine direction based on the target's position
        if (this.getPosX() < target.getPosX()) {
            // Moving right
            currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
        } else if (this.getPosX() > target.getPosX()) {
            // Moving left
            currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
        } else {
            // Moving down
            currentFrame = walkDownAnimation.getKeyFrame(stateTime, true);
        }
    }

    /**
     * Fires a projectile towards the player.
     *
     * @param player The player to target.
     */
    public void shotProjectile(Player player) {
        new BossProjectiles((this.posX + 1.6f), (this.posY + 1f), this, player);
        System.out.println("Boss life: " + this.getHp());
    }

    /**
     * Gets the current animation frame of the boss.
     *
     * @return The current TextureRegion frame.
     */
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    @Override
    public void isDead() {
        if (this.hp <= 0) {
            entities.remove(this);
            this.dispose();
            this.stopShooting();
            this.isDead = true;
        }
    }

    /**
     * Starts the boss's shooting behavior.
     *
     * @param player The player to target.
     */
    public void startShooting(Player player) {
        shootingTask = new Task() {
            @Override
            public void run() {
                shotProjectile(player);
            }
        };
        Timer.schedule(shootingTask, 0, shootingRate);
    }

    /**
     * Stops the boss's shooting behavior.
     */
    public void stopShooting() {
        if (shootingTask != null) {
            shootingTask.cancel();
            shootingTask = null;
        }
    }

    /**
     * Gets the hitbox of the boss for collision detection.
     *
     * @return The Rectangle representing the hitbox.
     */
    public Rectangle getHitBox() {
        float hitboxWidth = sizeX / 2;
        float hitboxHeight = sizeY / 2;
        float centerX = this.posX + sizeX / 3;
        float centerY = this.posY + sizeY / 3;
        return new Rectangle(centerX - hitboxWidth / 3, centerY - hitboxHeight / 3, hitboxWidth, hitboxHeight);
    }
}
