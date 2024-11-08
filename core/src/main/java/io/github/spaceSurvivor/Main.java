package io.github.spaceSurvivor;

import io.github.spaceSurvivor.monsters.Trouille;
import io.github.spaceSurvivor.monsters.Xela;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private Player player;
    private Xela xela;
    private Trouille trouille;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player();
        trouille = new Trouille();
        xela = new Xela();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        player.move();
        xela.move(player);
        trouille.move(player);
        batch.begin();
        player.renderEntity(batch);
        xela.renderEntity(batch);
        trouille.renderEntity(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        xela.dispose();
        trouille.dispose();
    }
}