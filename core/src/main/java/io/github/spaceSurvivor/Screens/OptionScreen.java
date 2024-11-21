package io.github.spaceSurvivor.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



import com.badlogic.gdx.Screen;
import io.github.spaceSurvivor.Main;

public class OptionScreen implements Screen {
    private final Stage stage;
    private Main game;
    private Texture backgroundOption;
    private final SpriteBatch batch;

    public OptionScreen(Main game) {
        this.game = game;
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        backgroundOption = new Texture("Background/Option.png");
    }

    @Override
    public void show() {
            Gdx.input.setInputProcessor(stage);
        }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(backgroundOption, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dispose() {
        backgroundOption.dispose();
    }
}
