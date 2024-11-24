package io.github.spaceSurvivor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.github.spaceSurvivor.weapons.Pewpew;
import io.github.spaceSurvivor.weapons.StoneThrown;
import io.github.spaceSurvivor.weapons.AutoNoob;
import io.github.spaceSurvivor.weapons.Weapon;
import io.github.spaceSurvivor.managers.CollisionManager;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents the player character in the game.
 * Manages player attributes, movements, weapons, and interactions.
 */
public class Player extends Movable {

    /** List of weapons owned by the player. */
    public static List<Weapon> weapons = new ArrayList<>();
    /** Indicates if the player is dead. */
    protected boolean isDead = false;
    /** Last X direction the player moved towards. */
    private float lastDirectionX = 0;
    /** Last Y direction the player moved towards. */
    private float lastDirectionY = 1;
    /** Current health points of the player. */
    private float hp = 200;
    /** Maximum health points of the player. */
    private float maxHp = 200;
    /** Current experience points of the player. */
    private int xp = 0;
    /** Current level of the player. */
    private int level = 1;
    /** Current score of the player. */
    public static int score = 0;
    /** Current X position of the player. */
    public static float posX = 950 * Map.getUnitScale();
    /** Current Y position of the player. */
    public static float posY = 800 * Map.getUnitScale();
    /** Indicates if the player can take damage. */
    private boolean canTakeDamage = true;
    /** Initial X position of the player. */
    private int initialX;
    /** Initial Y position of the player. */
    private int initialY;

    /** Animation for walking right. */
    private Animation<TextureRegion> walkRightAnimation;
    /** Animation for walking left. */
    private Animation<TextureRegion> walkLeftAnimation;
    /** Current frame of the player's animation. */
    private TextureRegion currentFrame;
    /** State time for animation control. */
    private float stateTime = 0f;

    /** Timer to manage damage immunity. */
    private float damageTimer = 0f;

    /**
     * Constructs a new Player instance with default attributes and initializes
     * animations.
     */
    public Player() {
        super(new Texture("Player/SpaceMarineSprites.png"), posX, posY, 85, 85, 150);
        loadAnimations(new Texture("Player/SpaceMarineSprites.png"));
        Player.weapons.add(new AutoNoob(this));
        this.initialX = (int) posX;
        this.initialY = (int) posY;
    }

    /**
     * Loads the walking animations from the provided sprite sheet.
     *
     * @param spriteSheet The texture containing the sprite sheet.
     */
    private void loadAnimations(Texture spriteSheet) {
        int frameWidth = 128;
        int frameHeight = 128;

        TextureRegion[][] frames = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        Array<TextureRegion> walkRightFrames = new Array<>();
        Array<TextureRegion> walkLeftFrames = new Array<>();

        for (int i = 0; i < 4; i++) {
            walkRightFrames.add(frames[1][i]);
        }

        for (int i = 0; i < 4; i++) {
            walkLeftFrames.add(frames[3][i]);
        }

        walkRightAnimation = new Animation<>(0.1f, walkRightFrames);
        walkLeftAnimation = new Animation<>(0.1f, walkLeftFrames);

        currentFrame = walkRightAnimation.getKeyFrame(0);
    }

    /**
     * Handles the player's movement based on input and manages collisions.
     *
     * @param collisionManager The collision manager to handle collisions.
     * @param map              The game map.
     */
    public void move(CollisionManager collisionManager, Map map) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean moved = false;
        float oldX = this.getPosX();
        float oldY = this.getPosY();

        stateTime += deltaTime;

        float deltaX = 0;
        float deltaY = 0;

        if (Gdx.input.isKeyPressed(Keys.A)) {
            deltaX -= this.getSpeed() * deltaTime;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            deltaX += this.getSpeed() * deltaTime;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            deltaY += this.getSpeed() * deltaTime;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            deltaY -= this.getSpeed() * deltaTime;
            moved = true;
        }

        if (moved) {
            this.setPosX(this.getPosX() + deltaX);
            this.setPosY(this.getPosY() + deltaY);

            float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (length != 0) {
                lastDirectionX = deltaX / length;
                lastDirectionY = deltaY / length;
            }

            if (deltaX < 0) {
                currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
            } else if (deltaX > 0) {
                currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
            } else {
                if (lastDirectionX < 0) {
                    currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
                } else {
                    currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
                }
            }
        } else {
            stateTime = 0;
            if (lastDirectionX < 0) {
                currentFrame = walkLeftAnimation.getKeyFrame(0);
            } else {
                currentFrame = walkRightAnimation.getKeyFrame(0);
            }
        }

        if (moved) {
            if (collisionManager.handleEntityMapCollision(this, map)) {
                this.setPosX(oldX);
                this.setPosY(oldY);
                System.out.println("Player collided with the map");
            }
        }
    }

    /**
     * Updates the player's state, including damage timers and weapon unlocks.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    public void update(float deltaTime) {
        if (damageTimer > 0f) {
            damageTimer -= deltaTime;
            if (damageTimer < 0f) {
                damageTimer = 0f;
            }
        }
        checkWeaponUnlocks();
    }

    /**
     * Checks and unlocks new weapons based on the player's level.
     */
    public void checkWeaponUnlocks() {
        if (level >= 5 && !hasWeapon(Pewpew.class)) {
            Player.weapons.add(new Pewpew(this));
            System.out.println("New weapon : Pewpew !");
        }
        if (level >= 10 && !hasWeapon(StoneThrown.class)) {
            Player.weapons.add(new StoneThrown(this));
            System.out.println("New weapon : StoneThrown !");
        }
    }

    /**
     * Checks if the player has a weapon of the specified class.
     *
     * @param weaponClass The class of the weapon to check.
     * @return True if the player has the weapon, false otherwise.
     */
    public boolean hasWeapon(Class<? extends Weapon> weaponClass) {
        for (Weapon weapon : Player.weapons) {
            if (weaponClass.isInstance(weapon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Renders the player sprite with appropriate coloring based on damage state.
     *
     * @param batch The sprite batch used for rendering.
     */
    public void render(SpriteBatch batch) {
        if (damageTimer > 0f) {
            batch.setColor(1f, 0f, 0f, 1f);
        } else {
            batch.setColor(1f, 1f, 1f, 1f);
        }

        batch.draw(getCurrentFrame(), getPosX(), getPosY(), sizeX, sizeY);

        batch.setColor(1f, 1f, 1f, 1f);
    }

    /**
     * Gets the last movement direction of the player.
     *
     * @return An array containing the X and Y components of the last direction.
     */
    public float[] getDirection() {
        return new float[] { lastDirectionX, lastDirectionY };
    }

    /**
     * Gets the current frame of the player's animation.
     *
     * @return The current TextureRegion frame.
     */
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Applies damage to the player and handles invincibility frames.
     *
     * @param damage The amount of damage to apply.
     */
    public void takeDamage(float damage) {
        if (canTakeDamage) {
            this.hp -= damage;
            isDead();
            canTakeDamage = false;
            damageTimer = 0.25f;

            Timer.schedule(new Task() {
                @Override
                public void run() {
                    canTakeDamage = true;
                }
            }, 0.25f);
        }
    }

    /**
     * Sets the player's current health points.
     *
     * @param newHp The new health points value.
     */
    public void setHp(float newHp) {
        if (newHp > this.maxHp) {
            this.hp = this.maxHp;
        } else {
            this.hp = newHp;
        }
    }

    /**
     * Sets the player's maximum health points.
     *
     * @param newMaxHp The new maximum health points value.
     */
    public void setMaxHp(float newMaxHp) {
        this.maxHp = newMaxHp;
    }

    /**
     * Checks if the player has gained a level based on experience points.
     */
    public void isLevelGained() {
        int xpRequired = 100 * level;
        if (this.xp >= xpRequired) {
            this.xp -= xpRequired;
            this.level += 1;
            this.maxHp += 25;
            this.hp = this.maxHp;
            System.out.println("Level up! You are now level " + this.level);
            System.out.println("HP: " + this.hp);
            System.out.println("XP: " + this.xp);
            System.out.println("==================================");
        }
    }

    /**
     * Disposes of the player's resources and resets static variables.
     */
    public void dispose() {
        for (Weapon weapon : new ArrayList<>(weapons)) {
            weapon.stopShooting();
        }
        weapons.clear();
        Player.posX = 950 * Map.getUnitScale();
        Player.posY = 800 * Map.getUnitScale();
        this.xp = 0;
        this.hp = 100;
    }

    /**
     * Checks if the player is dead and handles death logic.
     */
    public void isDead() {
        if (this.hp <= 0) {
            entities.remove(this);
            this.dispose();
            this.isDead = true;
        }
    }

    /**
     * Gets the dead status of the player.
     *
     * @return True if the player is dead, false otherwise.
     */
    public boolean getIsDead() {
        return this.isDead;
    }

    /**
     * Sets the player's X position.
     *
     * @param posX The new X position.
     */
    public void setPosX(float posX) {
        Player.posX = posX;
    }

    /**
     * Sets the player's Y position.
     *
     * @param posY The new Y position.
     */
    public void setPosY(float posY) {
        Player.posY = posY;
    }

    /**
     * Adds experience points to the player and checks for level up.
     *
     * @param xpToAdd The amount of experience points to add.
     */
    public void setXp(int xpToAdd) {
        this.xp += xpToAdd;
        isLevelGained();
    }

    /**
     * Sets the player's level.
     *
     * @param newLevel The new level value.
     */
    public void setLevel(int newLevel) {
        this.level = newLevel;
    }

    /**
     * Adds points to the player's score.
     *
     * @param points The number of points to add.
     */
    public static void addScore(int points) {
        Player.score += points;
    }

    // ====================== GETTERS ======================

    /**
     * Gets the player's current score.
     *
     * @return The current score.
     */
    public int getScore() {
        return Player.score;
    }

    /**
     * Gets the player's current level.
     *
     * @return The current level.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Gets the player's current X position.
     *
     * @return The X position.
     */
    public float getPosX() {
        return Player.posX;
    }

    /**
     * Gets the player's current Y position.
     *
     * @return The Y position.
     */
    public float getPosY() {
        return Player.posY;
    }

    /**
     * Gets the player's current health points.
     *
     * @return The current health points.
     */
    public float getHp() {
        return this.hp;
    }

    /**
     * Gets the player's maximum health points.
     *
     * @return The maximum health points.
     */
    public float getMaxHp() {
        return this.maxHp;
    }

    /**
     * Gets the player's hitbox as a Rectangle.
     *
     * @return The hitbox rectangle.
     */
    public Rectangle getHitBox() {
        float hitboxWidth = sizeX / 2;
        float hitboxHeight = sizeY / 2;
        float centerX = Player.posX + sizeX / 2;
        float centerY = Player.posY + sizeY / 2;
        return new Rectangle(centerX - hitboxWidth / 2, centerY - hitboxHeight / 2, hitboxWidth, hitboxHeight);
    }

    /**
     * Gets the player's initial X position.
     *
     * @return The initial X position.
     */
    public float getInitialX() {
        return this.initialX;
    }

    /**
     * Gets the player's initial Y position.
     *
     * @return The initial Y position.
     */
    public float getInitialY() {
        return this.initialY;
    }

    /**
     * Gets the player's current experience points.
     *
     * @return The current experience points.
     */
    public int getXp() {
        return this.xp;
    }
}
