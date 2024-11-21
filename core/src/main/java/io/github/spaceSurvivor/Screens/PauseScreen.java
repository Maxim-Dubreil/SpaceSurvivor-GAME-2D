package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.spaceSurvivor.Main;
import com.badlogic.gdx.graphics.Texture;
import io.github.spaceSurvivor.Player;
import io.github.spaceSurvivor.weapons.Weapon;

public class PauseScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final GameScreen gameScreen;
    private final Skin skin;
    private final BitmapFont font;
    private final Player player;

    public PauseScreen(Main game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));
        player = gameScreen.getPlayer();

        Texture resumeTexture = new Texture(Gdx.files.internal("buttons/resume_up.png"));
        Texture resumeOverTexture = new Texture(Gdx.files.internal("buttons/resume_over.png"));
        Texture resumePressedTexture = new Texture(Gdx.files.internal("buttons/resume_down.png"));

        Texture menuTexture = new Texture(Gdx.files.internal("buttons/menu_up.png"));
        Texture menuOverTexture = new Texture(Gdx.files.internal("buttons/menu_over.png"));
        Texture menuPressedTexture = new Texture(Gdx.files.internal("buttons/menu_down.png"));

        Texture quitTexture = new Texture(Gdx.files.internal("buttons/quit_up.png"));
        Texture quitOverTexture = new Texture(Gdx.files.internal("buttons/quit_over.png"));
        Texture quitPressedTexture = new Texture(Gdx.files.internal("buttons/quit_down.png"));

        // Création des styles pour les ImageButton
        ImageButton.ImageButtonStyle resumeButtonStyle = new ImageButton.ImageButtonStyle();
        resumeButtonStyle.up = new TextureRegionDrawable(new TextureRegion(resumeTexture));
        resumeButtonStyle.over = new TextureRegionDrawable(new TextureRegion(resumeOverTexture));
        resumeButtonStyle.down = new TextureRegionDrawable(new TextureRegion(resumePressedTexture));

        ImageButton.ImageButtonStyle menuButtonStyle = new ImageButton.ImageButtonStyle();
        menuButtonStyle.up = new TextureRegionDrawable(new TextureRegion(menuTexture));
        menuButtonStyle.over = new TextureRegionDrawable(new TextureRegion(menuOverTexture));
        menuButtonStyle.down = new TextureRegionDrawable(new TextureRegion(menuPressedTexture));

        ImageButton.ImageButtonStyle quitButtonStyle = new ImageButton.ImageButtonStyle();
        quitButtonStyle.up = new TextureRegionDrawable(new TextureRegion(quitTexture));
        quitButtonStyle.over = new TextureRegionDrawable(new TextureRegion(quitOverTexture));
        quitButtonStyle.down = new TextureRegionDrawable(new TextureRegion(quitPressedTexture));

        // Création des boutons
        ImageButton resumeButton = new ImageButton(resumeButtonStyle);
        ImageButton returnMenuButton = new ImageButton(menuButtonStyle);
        ImageButton quitButton = new ImageButton(quitButtonStyle);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resume();
            }
        });

        returnMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("PauseScreen", "Quit button clicked, returning to main menu...");
                returnToMainMenu();
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

        // AJOUT BOUTON DANS TABLE
        table.add(resumeButton).padTop(100);
        table.row().pad(10);
        table.add(returnMenuButton);
        table.row();
        table.add(quitButton);

        /*
         * Bouoton note :
         * Meme largeur
         * Meme hauteur
         * Changer return to menu par menu
         */

        // WINDOW
        Window pauseWindow = new Window("", skin);
        pauseWindow.setModal(true);
        pauseWindow.center();

        // TAILLE WINDOW
        pauseWindow.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());

        // BACKGROUND WINDOW
        Texture transparentTexture = new Texture(Gdx.files.internal("ui/backgroundPauseScreenv3.png"));
        TextureRegionDrawable transparentDrawable = new TextureRegionDrawable(new TextureRegion(transparentTexture));
        pauseWindow.setBackground(transparentDrawable);

        // AJOUT TABLE DANS WINDOW
        pauseWindow.add(table).pad(0).fill();

        // AJOUT WINDOW DANS STAGE
        stage.addActor(pauseWindow);

        // CENTRANGE WINDOW
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
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && game.getScreen() == this) {
            resume();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

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
