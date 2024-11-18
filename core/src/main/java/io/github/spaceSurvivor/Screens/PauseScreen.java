package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.spaceSurvivor.Main;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;


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
        table.center(); // Centre le tableau

        //TITRE
        Label titleLabel = new Label("Game Pause", skin);
        titleLabel.setAlignment(Align.center);
        table.add(titleLabel).expandX().fillX().padBottom(20);
        table.row();

        //AJOUT BOUTON DANS TABLE
        table.add(resumeButton).fillX().uniformX().height(60).pad(10);
        table.row();
        table.add(optionsButton).fillX().uniformX().height(60).pad(10);
        table.row();
        table.add(returnMenuButton).fillX().uniformX().height(60).pad(10);

        //WINDOW
        Window pauseWindow = new Window("", skin);
        pauseWindow.setModal(true);
        pauseWindow.center();

        //TAILLE WINDOW
        pauseWindow.setSize(500, 400);

        //BACKGROUND WINDOW
        Texture transparentTexture = new Texture(Gdx.files.internal("ui/backgroundPauseScreen.png"));
        TextureRegionDrawable transparentDrawable = new TextureRegionDrawable(new TextureRegion(transparentTexture));
        pauseWindow.setBackground(transparentDrawable);

        //AJOUT TABLE DANS WINDOW
        pauseWindow.add(table).pad(20).fill();

        //AJOUT WINDOW DANS STAGE
        stage.addActor(pauseWindow);

        //CENTRANGE WINDOW
        centerWindow(pauseWindow);
    }

    private void centerWindow(Window pauseWindow) {
        // Récupérez la largeur et la hauteur du stage (écran)
        float stageWidth = stage.getViewport().getWorldWidth();
        float stageHeight = stage.getViewport().getWorldHeight();

        // Récupérez la largeur et la hauteur de la fenêtre
        float windowWidth = pauseWindow.getWidth();
        float windowHeight = pauseWindow.getHeight();

        // Calculer la position pour centrer la fenêtre
        float x = (stageWidth - windowWidth) / 2;
        float y = (stageHeight - windowHeight) / 2;
        pauseWindow.setPosition(x, y);
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
