package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.spaceSurvivor.*;

import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.managers.ProgressionManager;
import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Main;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.dropable.FireSpeedBuff;
import io.github.spaceSurvivor.dropable.HealBuff;
import io.github.spaceSurvivor.dropable.MoveSpeedBuff;

import io.github.spaceSurvivor.managers.AudioManager;
import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.monsters.Boss;

import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.monsters.Trouille;
import io.github.spaceSurvivor.monsters.Xela;
import io.github.spaceSurvivor.projectiles.Projectile;
import io.github.spaceSurvivor.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private Player player;
    private Map map;
    private final CollisionManager collisionManager;

    private ProgressionManager progressionManager;

    private final List<Trouille> trouilles = new ArrayList<>();
    private final List<Xela> xelas = new ArrayList<>();
    private final Boss boss;
    private ShapeRenderer shapeRenderer;
    private boolean showHitboxes = false;

    private boolean isPaused = false;
    private final Stage stage;
    private final Skin skin;

    private Label waveMessageLabel;
    private Label hpLabel;
    private Label scoreLabel;

    private AudioManager audioManager;

    public GameScreen(Main game, SpriteBatch batch) {
        Gdx.app.log("GameScreen", "New instance of GameScreen created !");

        this.game = game;
        this.batch = batch;
        this.collisionManager = new CollisionManager();

        this.map = new Map("Map/SpaceSurvivorNewMap.tmx");
        this.map.initCamera();
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.player = new Player();
        this.boss = new Boss(900, 900, player);
        shapeRenderer = new ShapeRenderer();

        this.progressionManager = new ProgressionManager(player, this);

        // ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        audioManager = game.getAudioManager();
        audioManager.playGameMusic();
        Gdx.app.log("AudioManager", "AudioManager initialized and game music should play.");

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

        Table table = new Table();
        table.top().right();
        table.setFillParent(true);
        table.add(pauseButton).padTop(35).padRight(35);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);

        table.setDebug(true);

        waveMessageLabel = new Label("", skin);
        waveMessageLabel.setFontScale(2f);
        waveMessageLabel.setVisible(true);
        stage.addActor(waveMessageLabel);

        hpLabel = new Label("", skin);
        hpLabel.setFontScale(2f);
        stage.addActor(hpLabel);

        scoreLabel = new Label("", skin);
        scoreLabel.setFontScale(2f);
        stage.addActor(scoreLabel);
    }

    public void displayWaveMessage(String message) {
        waveMessageLabel.setText(message);
        waveMessageLabel.pack();
        float stageWidth = stage.getViewport().getWorldWidth();
        float stageHeight = stage.getViewport().getWorldHeight();
        waveMessageLabel.setPosition(stageWidth / 2f - waveMessageLabel.getWidth() / 2f, stageHeight - 50);

    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

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
            game.setScreen(new GameOverScreen(game, this));
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

        batch.setProjectionMatrix(map.getCamera().combined);
        batch.begin();
        for (Entity entity : entitiesCopy) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                ((Player) entity).render(batch);
                // batch.draw(player.getCurrentFrame(), player.getPosX(), player.getPosY(),
                // player.getSizeX(),
                // player.getSizeY());
            }
            if (entity instanceof Boss) {
                batch.draw(boss.getCurrentFrame(), boss.getPosX(), boss.getPosY(), boss.getSizeX(), boss.getSizeY());

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

    private void updateLabels() {
        float stageWidth = stage.getViewport().getWorldWidth();
        float stageHeight = stage.getViewport().getWorldHeight();

        waveMessageLabel.setPosition(stageWidth / 2f - waveMessageLabel.getWidth() / 2f, stageHeight - 50);

        hpLabel.setText("HP: " + (int) player.getHp());
        hpLabel.pack();
        hpLabel.setPosition(10, stageHeight - hpLabel.getHeight() - 10);

        scoreLabel.setText("Score: " + player.getScore());
        scoreLabel.pack();
        scoreLabel.setPosition(stageWidth - scoreLabel.getWidth() - 10, stageHeight - scoreLabel.getHeight() - 10);
    }

    private void checkAllCollisions() {
        collisionManager.handleEntityMapCollision(player, map);
        collisionManager.handleEntityMapCollision(boss, map);
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

    @Override
    public void pause() {
        if (!isPaused) {
            setPaused(true);
            Gdx.input.setInputProcessor(null);
            for (Weapon weapon : Weapon.weapons) {
                weapon.stopShooting();
            }
            boss.stopShooting();
            game.setScreen(new PauseScreen(game));
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        for (Entity entity : Entity.entities) {
            entity.dispose();
        }
        Entity.entities.clear();
        stage.dispose();
        skin.dispose();
        batch.dispose();
        map.dispose();
        shapeRenderer.dispose();
        audioManager.dispose();

    }

    public void resetGame() {
        for (Weapon weapon : Weapon.weapons) {
            weapon.stopShooting();
        }
        boss.stopShooting();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void resume() {
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            showHitboxes = !showHitboxes;
        }
    }
}
