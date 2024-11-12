package io.github.spaceSurvivor;

import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.monsters.Trouille;
import io.github.spaceSurvivor.monsters.Xela;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter, Game {

    private SpriteBatch batch;
    private Player player;
    private Xela xela;
    private Trouille trouille;


    @Override
    public void create() {
        batch = new SpriteBatch();
        // Démarre avec l'écran du menu principal
        this.setScreen(new MainMenuScreen(this));
      

    }

    public void startGame() {
        // Méthode appelée depuis MainMenuScreen pour passer au jeu
        player = new Player();
        trouille = new Trouille();
        xela = new Xela();
        this.setScreen(new GameScreen(player));
    }
    @Override
    public void render() {
        super.render();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        player.move();
        xela.move(player);
        trouille.move(player);

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

                if (entityA.getBoundingBox().overlaps(entityB.getBoundingBox())) {
                    handleCollision(entityA, entityB);
                }
            }
        }
    }

    private void handleCollision(Entity entityA, Entity entityB) {
        if (entityA instanceof Player && entityB instanceof Monster
                || entityA instanceof Monster && entityB instanceof Player) {
            handlePlayerMonsterCollision();
        } else if (entityA instanceof Monster && entityB instanceof Monster) {
            handleMonsterMonsterCollision();
        }
    }

    private void handlePlayerMonsterCollision() {
        System.out.println("Player collided with a Monster!");
    }

    private void handleMonsterMonsterCollision() {
        System.out.println("Monster and Monster collided !");
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Entity entity : Entity.entities) {
            entity.dispose();
        }
    }
}
