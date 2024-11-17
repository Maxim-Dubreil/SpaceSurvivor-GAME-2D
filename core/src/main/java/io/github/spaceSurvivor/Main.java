package io.github.spaceSurvivor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.spaceSurvivor.Screens.GameScreen;
import io.github.spaceSurvivor.Screens.MainMenuScreen;

public class Main extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }


    public void startGame() {
        resetGame();

        GameScreen gameScreen = new GameScreen(this, batch);
        this.setScreen(gameScreen);
    }

    private void resetGame() {
        Entity.entities.clear();// Vider toutes les entités existantes
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
}
