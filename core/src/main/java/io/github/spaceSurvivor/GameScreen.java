package io.github.spaceSurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    private final Player player;

    public GameScreen(Player player) {
        this.player = player;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Efface l'écran avec une couleur de fond
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Dessine le joueur et le fait bouger
        player.renderShape();
        player.move();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        player.dispose();
    }
}
