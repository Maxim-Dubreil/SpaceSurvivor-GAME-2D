// package io.github.spaceSurvivor;

// import io.github.spaceSurvivor.managers.CollisionManager;
// import io.github.spaceSurvivor.monsters.Monster;
// import io.github.spaceSurvivor.monsters.Trouille;
// import io.github.spaceSurvivor.monsters.Xela;
// import io.github.spaceSurvivor.projectiles.Projectile;

// import java.util.ArrayList;
// import java.util.List;

// import com.badlogic.gdx.Screen;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.utils.ScreenUtils;

// public class BackupGameScreen implements Screen {

// private final SpriteBatch batch;
// private Player player;
// private Map map;
// private CollisionManager collisionManager;
// private List<Trouille> trouilles = new ArrayList<>();
// private List<Xela> xelas = new ArrayList<>();

// public BackupGameScreen(SpriteBatch batch) {
// this.batch = batch;
// collisionManager = new CollisionManager();
// player = new Player();
// spawnMonstersInArc(20, 20, 500, 500, 680, 0, 180);
// map = new Map("Map/SpaceSurvivorMapTemple.tmx");
// map.initCamera();
// }

// public void spawnMonstersInArc(int numTrouilles, int numXelas, float centerX,
// float centerY, float radius,
// float startAngle, float endAngle) {
// float angleStepTrouille = (endAngle - startAngle) / (numTrouilles - 1); //
// Angle entre chaque Trouille
// float angleStepXela = (endAngle - startAngle) / (numXelas - 1); // Angle
// entre chaque Xela

// for (int i = 0; i < numTrouilles; i++) {
// float angle = startAngle + i * angleStepTrouille;
// float posX = centerX + (float) Math.cos(Math.toRadians(angle)) * radius;
// float posY = centerY + (float) Math.sin(Math.toRadians(angle)) * radius;
// trouilles.add(new Trouille(posX, posY));
// }

// for (int i = 0; i < numXelas; i++) {
// float angle = startAngle + i * angleStepXela;
// float posX = centerX + (float) Math.cos(Math.toRadians(angle)) * radius;
// float posY = centerY + (float) Math.sin(Math.toRadians(angle)) * radius;
// xelas.add(new Xela(posX, posY));
// }
// }

// @Override
// public void show() {
// // Initialisation supplémentaire si nécessaire
// }

// @Override
// public void render(float delta) {
// ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
// List<Entity> entitiesCopy = new ArrayList<>(Entity.entities);

// map.render();
// map.UpdateCamera(player.getPosX(), player.getPosY());

// player.move(collisionManager, map);

// for (Entity entity : entitiesCopy) {
// if (entity instanceof Monster) {
// ((Monster) entity).move(player, collisionManager, map);
// }
// if (entity instanceof Projectile) {
// ((Projectile) entity).move(collisionManager, map);
// }
// }

// batch.setProjectionMatrix(map.getCamera().combined);
// batch.begin();
// for (Entity entity : Entity.entities) {
// if (entity instanceof Player) {
// Player player = (Player) entity;
// batch.draw(player.getCurrentFrame(), player.getPosX(), player.getPosY(),
// player.getSizeX(),
// player.getSizeY());
// } else {
// entity.renderEntity(batch);
// }
// }
// batch.end();

// checkAllCollisions();
// }

// private void checkAllCollisions() {
// collisionManager.handleEntityMapCollision(player, map);

// for (Trouille trouille : trouilles) {
// collisionManager.handleEntityMapCollision(trouille, map);
// }

// for (Xela xela : xelas) {
// collisionManager.handleEntityMapCollision(xela, map);
// }

// for (int i = 0; i < Entity.entities.size(); i++) {
// for (int j = i + 1; j < Entity.entities.size(); j++) {
// Entity entityA = Entity.entities.get(i);
// Entity entityB = Entity.entities.get(j);

// if (collisionManager.isColliding(entityA, entityB)) {
// collisionManager.handleCollision(entityA, entityB);
// }
// }
// }
// }

// @Override
// public void resize(int width, int height) {
// }

// @Override
// public void pause() {
// }

// @Override
// public void resume() {
// }

// @Override
// public void hide() {
// }

// @Override
// public void dispose() {
// for (Entity entity : Entity.entities) {
// entity.dispose();
// }
// Entity.entities.clear();
// }
// }
