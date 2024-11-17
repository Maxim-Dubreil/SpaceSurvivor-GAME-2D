package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.Main;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.managers.CollisionManager;
import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.monsters.Trouille;
import io.github.spaceSurvivor.monsters.Xela;
import io.github.spaceSurvivor.projectiles.Projectile;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final Player player;
    private final Map map;
    private final CollisionManager collisionManager;
    private final List<Trouille> trouilles = new ArrayList<>();
    private final List<Xela> xelas = new ArrayList<>();

    private boolean isPaused = false;
    private final Stage stage;
    private final Skin skin;

    public GameScreen(Main game, SpriteBatch batch) {
        Gdx.app.log("GameScreen", "Nouvelle instance de GameScreen créée !");

        this.game = game;
        this.batch = batch;
        this.collisionManager = new CollisionManager();
        this.player = new Player();
        spawnMonstersInArc(20, 20, 500, 500, 680, 0, 180);

        this.map = new Map("Map/SpaceSurvivorMapTemple.tmx");
        this.map.initCamera();
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        ImageButtonStyle style = new ImageButtonStyle();
        Texture pauseTextureNormal = new Texture(Gdx.files.internal("ui/pauseButton.png"));
        Texture pauseTextureDown = new Texture(Gdx.files.internal("ui/pauseButtonDown.png"));
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
        //table.setDebug(true);
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause();
            return;
        }
        if (isPaused) {
            stage.act(delta);
            stage.draw();
            return;
        }

        List<Entity> entitiesCopy = new ArrayList<>(Entity.entities);
        map.render();
        map.UpdateCamera(player.getPosX(), player.getPosY());
        player.move(collisionManager, map);

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
        for (Entity entity : Entity.entities) {
            entity.renderEntity(batch);
        }
        batch.end();

        checkAllCollisions();
        stage.act(delta);
        stage.draw();
    }

    private void checkAllCollisions() {
        collisionManager.handleEntityMapCollision(player, map);
        for (Trouille trouille : trouilles) {
            collisionManager.handleEntityMapCollision(trouille, map);
        }
        for (Xela xela : xelas) {
            collisionManager.handleEntityMapCollision(xela, map);
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
        setPaused(true);
        Gdx.input.setInputProcessor(null);
        game.setScreen(new PauseScreen(game, this));
    }

    public void spawnMonstersInArc(int numTrouilles, int numXelas, float centerX, float centerY, float radius,
                                   float startAngle, float endAngle) {
        float angleStepTrouille = (endAngle - startAngle) / (numTrouilles - 1); // Angle entre chaque Trouille
        float angleStepXela = (endAngle - startAngle) / (numXelas - 1); // Angle entre chaque Xela
        for (int i = 0; i < numTrouilles; i++) {
            float angle = startAngle + i * angleStepTrouille;
            float posX = centerX + (float) Math.cos(Math.toRadians(angle)) * radius;
            float posY = centerY + (float) Math.sin(Math.toRadians(angle)) * radius;
            trouilles.add(new Trouille(posX, posY));
        }
        for (int i = 0; i < numXelas; i++) {
            float angle = startAngle + i * angleStepXela;
            float posX = centerX + (float) Math.cos(Math.toRadians(angle)) * radius;
            float posY = centerY + (float) Math.sin(Math.toRadians(angle)) * radius;
            xelas.add(new Xela(posX, posY));
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
    public void resume() {}

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
    }
}

