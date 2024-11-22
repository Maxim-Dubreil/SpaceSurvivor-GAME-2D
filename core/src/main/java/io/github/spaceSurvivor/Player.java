package io.github.spaceSurvivor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.github.spaceSurvivor.weapons.Pewpew;
import io.github.spaceSurvivor.weapons.StoneThrown;
import io.github.spaceSurvivor.weapons.Weapon;
import io.github.spaceSurvivor.managers.CollisionManager;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Player extends Movable {

    public static List<Weapon> weapons = new ArrayList<>();
    protected boolean isDead = false;
    private float lastDirectionX = 0;
    private float lastDirectionY = 1;
    private float hp = 200;
    private float maxHp = 200;
    private int xp = 0;
    private int level = 0;
    public static float posX = 600 * Map.getUnitScale();
    public static float posY = 600 * Map.getUnitScale();
    private boolean canTakeDamage = true;
    private int initialX;
    private int initialY;

    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private TextureRegion currentFrame;
    private float stateTime = 0f;
    private Game game;

    public Player() {
        super(new Texture("Player/SpaceMarineSprites.png"), posX, posY, 85, 85, 150);
        loadAnimations(new Texture("Player/SpaceMarineSprites.png"));
        Player.weapons.add(new Pewpew(this));
        // Player.weapons.add(new AutoNoob(this));
        Player.weapons.add(new StoneThrown(this));
        this.game = game;
        this.initialX = (int) posX;
        this.initialY = (int) posY;
    }

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

    @Override
    public float[] getDirection() {
        return new float[] { lastDirectionX, lastDirectionY };
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void takeDamage(float damage) {
        if (canTakeDamage) {
            this.hp -= damage;
            isDead();
            canTakeDamage = false;
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    canTakeDamage = true;
                }
            }, 0.25f);
        }
    }

    public void setHp(float newHp) {
        if (newHp > this.maxHp) {
            this.hp = this.maxHp;
        } else {
            this.hp = newHp;
        }
    }

    public void setMaxHp(float newMaxHp) {
        this.maxHp = newMaxHp;
    }

    public void isLevelGained() {
        int xpRequired = 100;
        if (this.xp >= xpRequired) {
            this.xp = 0;
            this.level += 1;
            this.hp = 100 + (this.level * 25);
            xpRequired = 100 * (this.level + 1);
            System.out.println("Level up! You are now level " + this.level);
            System.out.println("HP: " + this.hp);
            System.out.println("XP: " + this.xp);
            System.out.println("==================================");
        }
    }

    public String getLevelText() {
        return "Level: " + this.level;
    }

    public void isDead() {
        if (this.hp <= 0) {
            entities.remove(this);
            this.dispose();
            this.isDead = true;


        }
    }
    public boolean getIsDead() {
        return this.isDead;
    }

    public void setPosX(float posX) {
        Player.posX = posX;
    }

    public void setPosY(float posY) {
        Player.posY = posY;
    }

    public void setXp(int newXp) {
        this.xp += newXp;
    }

    public void setLevel(int newLevel) {
        this.hp = newLevel;
    }

    // ====================== GETTERS ======================

    public float getLevel() {
        return this.level;
    }

    public float getPosX() {
        return Player.posX;
    }

    public float getPosY() {
        return Player.posY;
    }

    public float getHp() {
        return this.hp;
    }

    public float getMaxHp() {
        return this.maxHp;
    }

    public Rectangle getHitBox() {
        float hitboxWidth = sizeX / 2;
        float hitboxHeight = sizeY / 2;
        float centerX = Player.posX + sizeX / 2;
        float centerY = Player.posY + sizeY / 2;
        return new Rectangle(centerX - hitboxWidth / 2, centerY - hitboxHeight / 2, hitboxWidth, hitboxHeight);
    }

    public float getInitialX() {
        return this.initialX;
    }

    public float getInitialY() {
        return this.initialY;
    }

    public int getXp() {
        return this.xp;
    }
}
