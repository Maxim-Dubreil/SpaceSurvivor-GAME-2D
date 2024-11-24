package io.github.spaceSurvivor.managers;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.monsters.Boss;
import io.github.spaceSurvivor.monsters.Monster;
import io.github.spaceSurvivor.Entity;
import io.github.spaceSurvivor.monsters.Trouille;
import io.github.spaceSurvivor.monsters.Xela;
import io.github.spaceSurvivor.dropable.HealBuff;
import io.github.spaceSurvivor.dropable.FireSpeedBuff;
import io.github.spaceSurvivor.dropable.MoveSpeedBuff;
import io.github.spaceSurvivor.Map;
import io.github.spaceSurvivor.Screens.GameScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the game's progression by spawning waves of monsters and buffs.
 */
public class ProgressionManager {

    /** The player instance. */
    private Player player;

    /** The game screen where the game is rendered. */
    private GameScreen gameScreen;

    /** The current wave number. */
    private int currentWave = 0;

    /** Spawn boundaries for monsters and buffs. */
    private final float spawnMinX = 220f;
    private final float spawnMaxX = 1600f;
    private final float spawnMinY = 500f;
    private final float spawnMaxY = 1350f;

    /** Total number of monsters in the current wave. */
    private int totalMonstersInCurrentWave = 0;

    /** List of monsters in the current wave. */
    private List<Monster> currentWaveMonsters = new ArrayList<>();

    /**
     * Constructs a new ProgressionManager.
     *
     * @param player     The player instance.
     * @param gameScreen The game screen.
     */
    public ProgressionManager(Player player, GameScreen gameScreen) {
        this.player = player;
        this.gameScreen = gameScreen;
    }

    /**
     * Updates the progression manager, checking if a new wave should be spawned.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    public void update(float deltaTime) {
        if (shouldSpawnNewWave()) {
            spawnWave();
        }
    }

    /**
     * Determines if a new wave should be spawned based on the number of live
     * monsters.
     *
     * @return True if a new wave should be spawned, false otherwise.
     */
    private boolean shouldSpawnNewWave() {
        int liveMonsters = getLiveMonstersCount();
        if (currentWave == 10) {
            // Wait until the boss is defeated
            return liveMonsters == 0;
        } else {
            int threshold = Math.max(1, (int) (totalMonstersInCurrentWave * 0.25f));
            return liveMonsters <= threshold;
        }
    }

    /**
     * Counts the number of live monsters in the current wave.
     *
     * @return The number of live monsters.
     */
    private int getLiveMonstersCount() {
        int count = 0;
        for (Monster monster : currentWaveMonsters) {
            if (Entity.entities.contains(monster)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Spawns a new wave of monsters and possibly a boss at wave 5.
     */
    private void spawnWave() {
        currentWave++;
        System.out.println("Spawning wave " + currentWave);

        if (currentWave == 10) {
            Boss boss = new Boss(900, 900, player);
            Entity.entities.add(boss);
            gameScreen.setBoss(boss);
            currentWaveMonsters.clear();
            currentWaveMonsters.add(boss);
            totalMonstersInCurrentWave = 1;
        } else {
            createMonstersForWave(currentWave);
        }

        if (currentWave == 1) {
            gameScreen.getStage().addAction(Actions.sequence(
                    Actions.delay(0.5f),
                    Actions.run(() -> gameScreen.displayWaveMessage("WAVE " + currentWave))));
        } else if (currentWave == 10) {
            gameScreen.displayWaveMessage("WAVE 10 : BOSS ");
        } else {
            gameScreen.displayWaveMessage("WAVE " + currentWave);
        }
    }

    /**
     * Creates monsters for the specified wave.
     *
     * @param waveNumber The wave number.
     */
    private void createMonstersForWave(int waveNumber) {
        int numberOfMonsters = waveNumber * 5;
        totalMonstersInCurrentWave = numberOfMonsters;
        currentWaveMonsters.clear();

        for (int i = 0; i < numberOfMonsters; i++) {
            Monster monster = generateRandomMonster();
            Entity.entities.add(monster);
            currentWaveMonsters.add(monster);
        }

        if (Math.random() < 0.2) {
            Entity.entities.add(generateRandomBuff());
        }
    }

    /**
     * Generates a random monster at a random spawn position.
     *
     * @return A new Monster instance.
     */
    private Monster generateRandomMonster() {
        float[] spawnPosition = getRandomSpawnPosition();
        double randomValue = Math.random();
        if (randomValue < 0.5) {
            return new Trouille(spawnPosition[0], spawnPosition[1]);
        } else {
            return new Xela(spawnPosition[0], spawnPosition[1]);
        }
    }

    /**
     * Generates a random spawn position outside the camera view.
     *
     * @return An array containing the X and Y coordinates.
     */
    private float[] getRandomSpawnPosition() {
        float x, y;
        int attempts = 0;
        do {
            x = spawnMinX + (float) (Math.random() * (spawnMaxX - spawnMinX));
            y = spawnMinY + (float) (Math.random() * (spawnMaxY - spawnMinY));
            attempts++;
            if (attempts > 100) {
                break;
            }
        } while (isInsideCameraView(x, y));
        return new float[] { x, y };
    }

    /**
     * Checks if a position is inside the camera's current view.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @return True if the position is inside the camera view, false otherwise.
     */
    private boolean isInsideCameraView(float x, float y) {
        OrthographicCamera camera = Map.camera;

        float cameraLeft = camera.position.x - camera.viewportWidth / 2;
        float cameraRight = camera.position.x + camera.viewportWidth / 2;
        float cameraBottom = camera.position.y - camera.viewportHeight / 2;
        float cameraTop = camera.position.y + camera.viewportHeight / 2;

        return x >= cameraLeft && x <= cameraRight && y >= cameraBottom && y <= cameraTop;
    }

    /**
     * Generates a random buff at a random spawn position.
     *
     * @return A new Entity representing the buff.
     */
    private Entity generateRandomBuff() {
        float[] spawnPosition = getRandomSpawnPosition();
        double randomValue = Math.random();
        if (randomValue < 0.33) {
            return new HealBuff(0.25f, spawnPosition[0], spawnPosition[1]);
        } else if (randomValue < 0.66) {
            return new FireSpeedBuff(5, spawnPosition[0], spawnPosition[1]);
        } else {
            return new MoveSpeedBuff(150, spawnPosition[0], spawnPosition[1]);
        }
    }
}
