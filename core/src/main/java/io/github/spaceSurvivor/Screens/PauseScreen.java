package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.github.spaceSurvivor.Main;

public class PauseScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final GameScreen gameScreen;
    private final Skin skin;


    public PauseScreen(Main game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));


        TextButton optionsButton = new TextButton("Settings", skin);
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton returnMenuButton = new TextButton("Return to menu", skin);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resume();
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Options button clicked, opening options");
            }
        });

        returnMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Quit button clicked, returning to main menu...");
                returnToMainMenu();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(resumeButton).fillX().uniformX().height(60);
        table.row().pad(10, 0, 10, 0);
        table.add(optionsButton).fillX().uniformX().height(60);
        table.row();
        table.add(returnMenuButton).fillX().uniformX().height(60);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.app.log("PauseScreen", "Affichage de l'écran Pause");
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            resume();

        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {
        gameScreen.setPaused(false);
        game.setScreen(gameScreen);
    }

    public void returnToMainMenu() {
        gameScreen.setPaused(false);
        game.MainMenuScreen();
    }
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
