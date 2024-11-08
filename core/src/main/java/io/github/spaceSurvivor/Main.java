package io.github.spaceSurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {

    private Player player1;
    private Monster monster1;
    private Monster monster2;
    private Monster monster3;
    private Monster monster4;
    private Monster monster5;

    @Override
    public void create() {
        player1 = new Player(100, 100, 50, 50, 150);
        monster1 = new Monster(500, 500, 35, 35, 100);
        monster2 = new Monster(650, 650, 35, 35, 100);
        monster3 = new Monster(580, 580, 35, 35, 100);
        monster4 = new Monster(420, 420, 35, 35, 100);
        monster5 = new Monster(310, 310, 35, 35, 100);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        player1.renderShape();
        player1.move();
        monster1.renderShape();
        monster1.move(player1);
        monster2.renderShape();
        monster2.move(player1);
        monster3.renderShape();
        monster3.move(player1);
        monster4.renderShape();
        monster4.move(player1);
        monster5.renderShape();
        monster5.move(player1);
    }

    @Override
    public void dispose() {
        player1.dispose();
        monster1.dispose();
    }
}