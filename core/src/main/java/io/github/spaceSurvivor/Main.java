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

    /*public void resetGame() {
        GameScreen gameScreen = new GameScreen(this, batch);
        this.setScreen(gameScreen);
    }**/

    public void startGame() {
        GameScreen gameScreen = new GameScreen(this, batch);
        this.setScreen(gameScreen);
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
