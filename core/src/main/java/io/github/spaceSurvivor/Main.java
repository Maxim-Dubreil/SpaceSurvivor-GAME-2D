package io.github.spaceSurvivor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.spaceSurvivor.Screens.GameScreen;
import io.github.spaceSurvivor.Screens.MainMenuScreen;
import io.github.spaceSurvivor.Screens.OptionScreen;
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

    /*public void resetGame() {
        GameScreen gameScreen = new GameScreen(this, batch);
        this.setScreen(gameScreen);
    }**/

    public void startGame() {
        if (gameScreen == null) {
            gameScreen = new GameScreen(this, batch);
        }
        pauseScreen = new PauseScreen(this);
        this.setScreen(gameScreen);
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Entity entity : Entity.entities) {
            entity.dispose();
        }
    }

    public void setFullScreen() {
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(displayMode);
    }

    public void setWindowedMode() {
        Gdx.graphics.setWindowedMode(1280, 720); // Taille de la fenêtre (par exemple 800x600)
    }

    public void MainMenuScreen() {
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
