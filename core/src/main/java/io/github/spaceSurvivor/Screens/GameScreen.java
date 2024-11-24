package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.spaceSurvivor.*;

import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.managers.ProgressionManager;

import io.github.spaceSurvivor.managers.AudioManager;
import io.github.spaceSurvivor.monsters.Boss;

import io.github.spaceSurvivor.monsters.Monster;

import io.github.spaceSurvivor.projectiles.Projectile;
import io.github.spaceSurvivor.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

/**
 * The main game screen where gameplay occurs.
 * Handles rendering, input processing, UI updates, and game logic.
 */
public class GameScreen implements Screen {
    /** The main game instance. */
    private final Main game;
    /** The SpriteBatch used for rendering. */
    private final SpriteBatch batch;
    /** The player character. */
    private Player player;
    /** The game map. */
    private Map map;
    /** Manages collision detection and handling. */
    private final CollisionManager collisionManager;

    /** Manages game progression, including spawning waves of enemies. */
    private ProgressionManager progressionManager;

    /** The boss enemy. */
    private Boss boss;
    /** Used for rendering shapes like hitboxes. */
    private ShapeRenderer shapeRenderer;
    /** Flag to indicate whether to show hitboxes. */
    private boolean showHitboxes = false;

    /** Indicates if the game is paused. */
    private boolean isPaused = false;
    /** The stage for UI elements. */
    private final Stage stage;
    /** The skin for UI styling. */
    private final Skin skin;

    /** Label to display wave messages. */
    private Label waveMessageLabel;
    /** Label to display the score. */
    private Label scoreLabel;
    /** Custom font used for labels. */
    private BitmapFont myFont;

    /** Manages audio playback. */
    private AudioManager audioManager;

    /** Progress bar representing the player's health. */
    private ProgressBar healthBar;
    /** Skin for the health bar UI elements. */
    private Skin skinHealthBar;

    /**
     * Constructs a new GameScreen instance.
     *
     * @param game  The main game instance.
     * @param batch The SpriteBatch used for rendering.
     */
    public GameScreen(Main game, SpriteBatch batch) {
        Gdx.app.log("GameScreen", "New instance of GameScreen created!");

        this.game = game;
        this.batch = batch;
        this.collisionManager = new CollisionManager();

        this.map = new Map("Map/SpaceSurvivorNewMap.tmx");
        this.map.initCamera();
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.player = new Player();
        // Removed direct instantiation of the boss
        this.boss = null;
        shapeRenderer = new ShapeRenderer();
        this.progressionManager = new ProgressionManager(player, this);
        this.skinHealthBar = new Skin(Gdx.files.internal("Skin/Pixthulhu/pixthulhu-ui.json"),
                new TextureAtlas(Gdx.files.internal("Skin/Pixthulhu/pixthulhu-ui.atlas")));

        initializeHealthBar();

        audioManager = game.getAudioManager();
        audioManager.playGameMusic();

        // Initialize pause button
        ImageButtonStyle style = new ImageButtonStyle();

        Texture pauseTextureNormal = new Texture(Gdx.files.internal("buttons/pauseButton.png"));
        Texture pauseTextureDown = new Texture(Gdx.files.internal("buttons/pauseButtonDown.png"));
        style.up = new TextureRegionDrawable(new TextureRegion(pauseTextureNormal));
        style.down = new TextureRegionDrawable(new TextureRegion(pauseTextureDown));

        ImageButton pauseButton = new ImageButton(style);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause();
            }
        });

        // Set up UI layout
        Table table = new Table();
        table.top().right();
        table.setFillParent(true);
        table.add(pauseButton).padTop(35).padRight(35);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);

        // Initialize custom font
        myFont = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));

        // Initialize labels for score and wave messages
        Label.LabelStyle labelCustom = new Label.LabelStyle();
        labelCustom.font = myFont;

        waveMessageLabel = new Label("", labelCustom);
        waveMessageLabel.setFontScale(1.5f);
        waveMessageLabel.setColor(Color.WHITE);
        waveMessageLabel.setVisible(true);
        stage.addActor(waveMessageLabel);

        scoreLabel = new Label("", labelCustom);
        scoreLabel.setFontScale(0.8f);
        scoreLabel.setColor(Color.YELLOW);
        stage.addActor(scoreLabel);
    }

    /**
     * Initializes the health bar UI element.
     */
    public void initializeHealthBar() {
        ProgressBar.ProgressBarStyle progressHealthBarStyle = skinHealthBar.get("health",
                ProgressBar.ProgressBarStyle.class);
        healthBar = new ProgressBar(0, player.getMaxHp(), 1, false, progressHealthBarStyle);
        healthBar.setValue(player.getHp());

        float screenWidth = stage.getViewport().getWorldWidth();
        float screenHeight = stage.getViewport().getWorldHeight();
        float barWidth = screenWidth * 0.3f;
        float barHeight = screenHeight * 0.03f;

        healthBar.setSize(barWidth, barHeight);
        healthBar.setPosition(40, screenHeight - barHeight - 40);

        stage.addActor(healthBar);
    }

    /**
     * Sets the boss instance in the game screen.
     *
     * @param boss The boss to set.
     */
    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    /**
     * Displays a wave message on the screen with a fade-in and fade-out effect.
     *
     * @param message The message to display.
     */
    public void displayWaveMessage(String message) {
        waveMessageLabel.setText(message);
        waveMessageLabel.pack();

        float stageWidth = stage.getViewport().getWorldWidth();
        float stageHeight = stage.getViewport().getWorldHeight();
        float labelX = stageWidth / 2f - waveMessageLabel.getWidth() / 2f;
        float labelY = stageHeight / 2f + (stageHeight / 4f) - waveMessageLabel.getHeight() / 2f;

        waveMessageLabel.setPosition(labelX, labelY);
        waveMessageLabel.setVisible(true);

        waveMessageLabel.clearActions();
        waveMessageLabel.addAction(Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(1f),
                Actions.delay(2f),
                Actions.fadeOut(0.5f),
                Actions.run(() -> waveMessageLabel.setVisible(false))));
    }

    /**
     * Updates the UI labels such as the score.
     */
    private void updateLabels() {
        float stageWidth = stage.getViewport().getWorldWidth();
        float stageHeight = stage.getViewport().getWorldHeight();

        // Update score label
        scoreLabel.setText("Score: " + player.getScore());
        scoreLabel.pack();
        scoreLabel.setPosition(
                stageWidth / 2f - scoreLabel.getWidth() / 2f,
                stageHeight - scoreLabel.getHeight() - 40);
    }

    /**
     * Renders the game screen.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (isPaused) {
            stage.act(delta);
            stage.draw();
            return;
        }

        if (player.getIsDead()) {
            System.out.println("Player is dead. Score before GameOverScreen: " + player.getScore());
            game.setScreen(new GameOverScreen(game));
        }

        progressionManager.update(delta);

        List<Entity> entitiesCopy = new ArrayList<>(Entity.entities);
        map.render();
        map.UpdateCamera(player.getPosX(), player.getPosY());
        player.move(collisionManager, map);
        player.update(delta);

        for (Entity entity : entitiesCopy) {
            if (entity instanceof Monster) {
                ((Monster) entity).move(player, collisionManager, map);
            }
            if (entity instanceof Projectile) {
                ((Projectile) entity).move(collisionManager, map);
            }
        }

        // Update health bar
        if (healthBar == null) {
            initializeHealthBar();
        }
        healthBar.setValue(player.getHp());
        healthBar.setRange(0, player.getMaxHp());

        batch.setProjectionMatrix(map.getCamera().combined);
        batch.begin();
        for (Entity entity : entitiesCopy) {
            if (entity instanceof Player) {
                ((Player) entity).render(batch);
            } else if (entity instanceof Boss) {
                batch.draw(((Boss) entity).getCurrentFrame(), entity.getPosX(), entity.getPosY(),
                        entity.getSizeX(), entity.getSizeY());
            } else {
                entity.renderEntity(batch);
            }
        }
        batch.end();

        if (showHitboxes) {
            shapeRenderer.setProjectionMatrix(map.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (Entity entity : entitiesCopy) {
                Rectangle hitbox = entity.getHitBox();
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            }
            shapeRenderer.end();
        }

        checkAllCollisions();

        updateLabels();

        stage.act(delta);
        stage.draw();
    }

    /**
     * Checks for all collisions in the game and handles them.
     */
    private void checkAllCollisions() {
        collisionManager.handleEntityMapCollision(player, map);
        if (boss != null) {
            collisionManager.handleEntityMapCollision(boss, map);
        }
        for (Entity entity : Entity.entities) {
            if (entity instanceof Movable && entity != player) {
                collisionManager.handleEntityMapCollision((Movable) entity, map);
            }
        }

        for (int i = 0; i < Entity.entities.size(); i++) {
            for (int j = i + 1; j < Entity.entities.size(); j++) {
                Entity entityA = Entity.entities.get(i);
                Entity entityB = Entity.entities.get(j);

                if (collisionManager.isColliding(entityA, entityB)) {
                    collisionManager.handleCollision(entityA, entityB);
                }
            }
        }
    }

    /**
     * Sets the paused state of the game.
     *
     * @param isPaused True to pause the game, false to resume.
     */
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * Pauses the game and transitions to the pause screen.
     */
    @Override
    public void pause() {
        if (!isPaused) {
            setPaused(true);
            Gdx.input.setInputProcessor(null);
            for (Weapon weapon : Weapon.weapons) {
                weapon.stopShooting();
            }
            if (boss != null) {
                boss.stopShooting();
            }
            game.setScreen(new PauseScreen(game));
        }
    }

    /**
     * Called when the screen becomes the current screen for the game.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen size changes.
     *
     * @param width  The new width in pixels.
     * @param height The new height in pixels.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Called when this screen is no longer the current screen.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Disposes of all resources used by the game screen.
     */
    @Override
    public void dispose() {
        List<Entity> entitiesCopy = new ArrayList<>(Entity.entities);
        for (Entity entity : entitiesCopy) {
            entity.dispose();
        }
        Entity.entities.clear();
        Player.posX = 950 * Map.getUnitScale();
        Player.posY = 800 * Map.getUnitScale();

        for (Weapon weapon : new ArrayList<>(Weapon.weapons)) {
            weapon.stopShooting();
        }
        Weapon.weapons.clear();

        if (boss != null) {
            boss.stopShooting();
            boss.dispose();
        }

        stage.dispose();
        skin.dispose();
        map.dispose();
        shapeRenderer.dispose();
        audioManager.dispose();
    }

    /**
     * Resets the game state, stopping weapons and boss shooting.
     */
    public void resetGame() {
        for (Weapon weapon : Weapon.weapons) {
            weapon.stopShooting();
        }
        if (boss != null) {
            boss.stopShooting();
        }
    }

    /**
     * Gets the player instance.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Called when the game is resumed from a paused state.
     */
    @Override
    public void resume() {
        // Implement resume functionality if needed
    }

    /**
     * Handles user input such as toggling hitbox visibility.
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            showHitboxes = !showHitboxes;
        }
    }

    /**
     * Gets the stage used for UI elements.
     *
     * @return The stage.
     */
    public Stage getStage() {
        return stage;
    }

}
