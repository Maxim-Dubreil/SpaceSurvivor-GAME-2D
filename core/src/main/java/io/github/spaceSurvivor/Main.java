package io.github.spaceSurvivor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.spaceSurvivor.Screens.GameScreen;
import io.github.spaceSurvivor.Screens.MainMenuScreen;
import io.github.spaceSurvivor.Screens.PauseScreen;
import io.github.spaceSurvivor.managers.AudioManager;

public class Main extends Game {
    private SpriteBatch batch;
    private GameScreen gameScreen;
    private PauseScreen pauseScreen;
    private AudioManager audioManager;

    @Override
    public void create() {
        setFullScreen();
        batch = new SpriteBatch();
        audioManager = new AudioManager();
        this.setScreen(new MainMenuScreen(this));
    }

    public void startGame() {
        if (gameScreen != null) {
            gameScreen.dispose();
            gameScreen = null;
        }
        gameScreen = new GameScreen(this, batch);
        pauseScreen = new PauseScreen(this);
        this.setScreen(gameScreen);
        Player.score = 0;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        if (pauseScreen != null) {
            pauseScreen.dispose();
        }
        if (audioManager != null) {
            audioManager.dispose();
        }
        batch.dispose();
    }

    public void setFullScreen() {
        if (!Gdx.graphics.isFullscreen()) {
            Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
            Gdx.graphics.setFullscreenMode(currentMode);
        }
    }

    public void setWindowedMode() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(1920, 1080);
        }
    }

    public void MainMenuScreen() {
        if (gameScreen != null) {
            gameScreen.dispose();
            gameScreen = null;
        }
        this.setScreen(new MainMenuScreen(this));
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public PauseScreen getPauseScreen() {
        return pauseScreen;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
}
