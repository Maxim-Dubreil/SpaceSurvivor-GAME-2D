package io.github.spaceSurvivor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.spaceSurvivor.Screens.GameScreen;
import io.github.spaceSurvivor.Screens.MainMenuScreen;
import io.github.spaceSurvivor.Screens.OptionScreen;
import io.github.spaceSurvivor.Screens.PauseScreen;

public class Main extends Game {
    private SpriteBatch batch;
    private GameScreen gameScreen;
    private PauseScreen pauseScreen;


    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    /*public void resetGame() {
        GameScreen gameScreen = new GameScreen(this, batch);
        this.setScreen(gameScreen);
    }**/

    public void startGame() {
        if (gameScreen == null) {
            gameScreen = new GameScreen(this, batch);  // Créez l'écran de jeu ici si nécessaire
        }
        pauseScreen = new PauseScreen(this);       // Créez l'écran de pause ici
        this.setScreen(gameScreen);                // Définit l'écran de jeu
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Entity entity : Entity.entities) {
            entity.dispose();
        }
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
}
