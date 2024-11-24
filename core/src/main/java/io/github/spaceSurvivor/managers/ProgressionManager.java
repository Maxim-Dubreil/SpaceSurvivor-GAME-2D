package io.github.spaceSurvivor.managers;

import io.github.spaceSurvivor.Player;
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

public class ProgressionManager {
    private Player player;
    private GameScreen gameScreen;
    private int currentWave = 0;

    private final float spawnMinX = 220f;
    private final float spawnMaxX = 1600f;
    private final float spawnMinY = 500f;
    private final float spawnMaxY = 1350f;

    private int totalMonstersInCurrentWave = 0;
    private List<Monster> currentWaveMonsters = new ArrayList<>();

    public ProgressionManager(Player player, GameScreen gameScreen) {
        this.player = player;
        this.gameScreen = gameScreen;
    }

    public void update(float deltaTime) {
        if (shouldSpawnNewWave()) {
            spawnWave();
        }
    }

    private boolean shouldSpawnNewWave() {
        int liveMonsters = getLiveMonstersCount();
        int threshold = Math.max(1, (int) (totalMonstersInCurrentWave * 0.25f));
        return liveMonsters <= threshold;
    }

    private int getLiveMonstersCount() {
        int count = 0;
        for (Monster monster : currentWaveMonsters) {
            if (Entity.entities.contains(monster)) {
                count++;
            }
        }
        return count;
    }

    private void spawnWave() {
        currentWave++;
        System.out.println("Spawning wave " + currentWave);
        createMonstersForWave(currentWave);

        gameScreen.displayWaveMessage("WAVE " + currentWave);
    }

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

    private Monster generateRandomMonster() {
        float[] spawnPosition = getRandomSpawnPosition();
        double randomValue = Math.random();
        if (randomValue < 0.5) {
            return new Trouille(spawnPosition[0], spawnPosition[1]);
        } else {
            return new Xela(spawnPosition[0], spawnPosition[1]);
        }
    }

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

    private boolean isInsideCameraView(float x, float y) {
        OrthographicCamera camera = Map.camera;

        float cameraLeft = camera.position.x - camera.viewportWidth / 2;
        float cameraRight = camera.position.x + camera.viewportWidth / 2;
        float cameraBottom = camera.position.y - camera.viewportHeight / 2;
        float cameraTop = camera.position.y + camera.viewportHeight / 2;

        return x >= cameraLeft && x <= cameraRight && y >= cameraBottom && y <= cameraTop;
    }

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
