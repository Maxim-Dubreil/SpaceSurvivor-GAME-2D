package io.github.spaceSurvivor;

import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.monsters.Trouille;
import io.github.spaceSurvivor.monsters.Xela;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    private final Main game;
    private final SpriteBatch batch;
    private Player player;
    private Trouille trouille;
    private Xela xela;
    private Map map;

    public GameScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        player = new Player();
        trouille = new Trouille();
        xela = new Xela();
        map = new Map("Map/SpaceSurvivorMapTemple.tmx");
        map.initCamera();
    }

    @Override
    public void show() {
        // Initialisation supplémentaire si nécessaire
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        map.render();
        map.UpdateCamera(player.getPosX(), player.getPosY());

        player.move();
        xela.move(player);
        trouille.move(player);

        batch.setProjectionMatrix(map.getCamera().combined);

        batch.begin();
        for (Entity entity : Entity.entities) {
            entity.renderEntity(batch);
        }
        batch.end();

        checkAllCollisions();
    }

    private void checkAllCollisions() {
        for (int i = 0; i < Entity.entities.size(); i++) {
            for (int j = i + 1; j < Entity.entities.size(); j++) {
                Entity entityA = Entity.entities.get(i);
                Entity entityB = Entity.entities.get(j);

                if (entityA.getHitBox().overlaps(entityB.getHitBox())) {
                    handleCollision(entityA, entityB);
                }
            }
        }
    }

    private void handleCollision(Entity entityA, Entity entityB) {
        if ((entityA instanceof Player && entityB instanceof Monster) ||
                (entityA instanceof Monster && entityB instanceof Player)) {
            Player player = (entityA instanceof Player) ? (Player) entityA : (Player) entityB;
            Monster monster = (entityA instanceof Monster) ? (Monster) entityA : (Monster) entityB;
            handlePlayerMonsterCollision(player, monster);
        } else if (entityA instanceof Monster && entityB instanceof Monster) {
            Monster monster1 = (Monster) entityA;
            Monster monster2 = (Monster) entityB;
            handleMonsterMonsterCollision(monster1, monster2);
        }
    }

    private void handlePlayerMonsterCollision(Player player, Monster monster) {
        System.out.println("Player collided with a Monster!");
    }

    private void handleMonsterMonsterCollision(Monster monster1, Monster monster2) {
        System.out.println("Monster and Monster collided!");
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        for (Entity entity : Entity.entities) {
            entity.dispose();
        }
        Entity.entities.clear();
    }
}
