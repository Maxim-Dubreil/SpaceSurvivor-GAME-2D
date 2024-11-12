package io.github.spaceSurvivor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */

public class Main extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Démarre avec l'écran du menu principal
        this.setScreen(new MainMenuScreen(this));
    }

    public void startGame() {
        // Méthode appelée depuis MainMenuScreen pour passer au jeu
        Player player1 = new Player(100, 100, 50, 50, new float[] { 0, 1, 0, 1 }, 150);
        this.setScreen(new GameScreen(player1));
    }
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
