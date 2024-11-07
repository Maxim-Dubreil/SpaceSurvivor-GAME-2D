package io.github.spaceSurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {

    private Player player1;

    @Override
    public void create() {
        player1 = new Player(100, 100, 50, 50, new float[] { 1, 0, 1, 0 }, 150);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        player1.renderShape();
        player1.move();
    }

    @Override
    public void dispose() {
        player1.dispose();
    }
}
