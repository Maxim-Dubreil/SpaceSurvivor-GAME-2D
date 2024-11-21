package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.spaceSurvivor.Main;
import com.badlogic.gdx.graphics.Texture;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.Screens.backOptions.OptionScreenPause;
import io.github.spaceSurvivor.weapons.Weapon;

public class PauseScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final GameScreen gameScreen;
    private final Skin skin;
    private final Player player;
    private Window pauseWindow;

    public PauseScreen(Main game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.player = gameScreen.getPlayer();

        initializeUI();
    }

    private void initializeUI() {
        ImageButton resumeButton = createButton("buttons/resume");
        ImageButton optionButton = createButton("buttons/options");
        ImageButton returnMenuButton = createButton("buttons/menu");
        ImageButton quitButton = createButton("buttons/quit");

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Resume button clicked, resuming the game...");
                resume();
            }
        });

        returnMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Quit button clicked, set option screen...");
                returnToMainMenu();
            }
        });

        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Option button clicked, returning to main menu...");
                game.setScreen(new OptionScreenPause(game));

            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "quit button clicked, quit the game");
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.center();
        table.add(resumeButton);
        table.row().pad(10);
        table.add(optionButton);
        table.row().pad(10);
        table.add(returnMenuButton);
        table.row();
        table.add(quitButton);


        pauseWindow = new Window("", skin);
        pauseWindow.setModal(true);
        pauseWindow.setMovable(false);
        pauseWindow.setBackground(createBackgroundDrawable("Background/Pause.png"));
        pauseWindow.add(table).expand().fill();

        pauseWindow.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        pauseWindow.setSize(800, 600);  // Taille de la fenêtre (ajuste selon tes besoins)

        centerActor(pauseWindow);
        stage.addActor(pauseWindow);
    }

    private ImageButton createButton(String basePath) {
        Texture upTexture = new Texture(Gdx.files.internal(basePath + "_up.png"));
        Texture overTexture = new Texture(Gdx.files.internal(basePath + "_over.png"));
        Texture downTexture = new Texture(Gdx.files.internal(basePath + "_down.png"));

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        buttonStyle.over = new TextureRegionDrawable(new TextureRegion(overTexture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(downTexture));

        return new ImageButton(buttonStyle);
    }


    private TextureRegionDrawable createBackgroundDrawable(String path) {
        Texture texture = new Texture(Gdx.files.internal(path));
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    private void centerActor(Actor actor) {
        float stageWidth = stage.getViewport().getWorldWidth();
        float stageHeight = stage.getViewport().getWorldHeight();
        float actorWidth = actor.getWidth();
        float actorHeight = actor.getHeight();

        Gdx.app.log("PauseScreen", "Stage size: " + stageWidth + "x" + stageHeight);
        Gdx.app.log("PauseScreen", "Actor size: " + actorWidth + "x" + actorHeight);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && game.getScreen() == this) {
            resume();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        pauseWindow.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());

        centerActor(pauseWindow);

    }


    @Override
    public void pause() {}

    @Override
    public void resume() {
        if (game.getScreen() != gameScreen) {

            gameScreen.setPaused(false);
            for (Weapon weapon : Weapon.weapons) {
                weapon.startShooting(player);
            }
            game.setScreen(gameScreen);
        }
    }

    public void returnToMainMenu() {
        gameScreen.setPaused(false);
        gameScreen.resetGame();
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
