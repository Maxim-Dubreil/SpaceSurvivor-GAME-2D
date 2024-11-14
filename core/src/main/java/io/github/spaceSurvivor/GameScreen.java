package io.github.spaceSurvivor;

import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.monsters.Trouille;
import io.github.spaceSurvivor.monsters.Xela;
import io.github.spaceSurvivor.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    // private final Main game;
    private final SpriteBatch batch;
    private Player player;
    private Map map;
    private List<Trouille> trouilles = new ArrayList<>();
    private List<Xela> xelas = new ArrayList<>();

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;

        player = new Player();
        spawnMonstersInArc(20, 20, 500, 500, 680, 0, 180);
        map = new Map("Map/SpaceSurvivorMapTemple.tmx");
        map.initCamera();
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
        // Initialisation supplémentaire si nécessaire
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        List<Entity> entitiesCopy = new ArrayList<>(Entity.entities);

        map.render();
        map.UpdateCamera(player.getPosX(), player.getPosY());

        player.move();

        for (Entity entity : entitiesCopy) {
            if (entity instanceof Monster) {
                ((Monster) entity).move(player);
            }
            if (entity instanceof Projectile) {
                ((Projectile) entity).move();
            }
        }

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
        } else if ((entityA instanceof Projectile && entityB instanceof Monster) ||
                (entityA instanceof Monster && entityB instanceof Projectile)) {
            Projectile projectile = (entityA instanceof Projectile) ? (Projectile) entityA : (Projectile) entityB;
            Monster monster = (entityA instanceof Monster) ? (Monster) entityA : (Monster) entityB;
            handleProjectileMonsterCollision(projectile, monster);
        } else if (entityA instanceof Monster && entityB instanceof Monster) {
            Monster monster1 = (Monster) entityA;
            Monster monster2 = (Monster) entityB;
            handleMonsterMonsterCollision(monster1, monster2);
        }
    }

    private void handlePlayerMonsterCollision(Player player, Monster monster) {
        System.out.println("Player collided with a Monster!");
        // player.takeDamage(monster.getDamage());
    }

    private void handleProjectileMonsterCollision(Projectile projectile, Monster monster) {
        System.out.println("Projectile a touché le Monster!");
        // monster.takeDamage(projectile.getDamages());
        Entity.entities.remove(projectile);
        projectile.dispose();
        Entity.entities.remove(monster);
        monster.dispose();
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
