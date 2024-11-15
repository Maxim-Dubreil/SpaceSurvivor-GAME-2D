package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.github.spaceSurvivor.Main;

public class MainMenuScreen implements Screen {

    private final Main game;
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;

    private final BitmapFont font;
    private final SpriteBatch batch;

    public MainMenuScreen(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        backgroundTexture = new Texture("background.png");

        // Initialisation BitmapFont et SpriteBatch
        font = new BitmapFont();
        batch = new SpriteBatch();

        // Initialisation des boutons
        TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        // Actions des boutons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("MainMenuScreen", "Play button clicked, starting game...");
                game.startGame(); // Lance le jeu
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Quitte l'application
            }
        });

        // Configuration de la table pour positionner les boutons
        Table table = new Table();
        table.setFillParent(true);
        table.add(playButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(optionsButton).fillX().uniformX();
        table.row();
        table.add(quitButton).fillX().uniformX();

        // Ajoute la table avec les boutons à la scène
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.app.log("MainMenuScreen", "Menu displayed");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f); // Efface l’écran avec une couleur de fond

        // Commencer à dessiner avec SpriteBatch
        batch.begin();

        // Affichage de l'image de fond
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Dessine l'image en
                                                                                                // fond

        // Dessiner le titre
        font.getData().setScale(3f); // Vous pouvez ajuster la taille de la police ici
        font.draw(batch, "Space Survivor", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 50); // Dessiner
                                                                                                              // le
                                                                                                              // texte
                                                                                                              // au
                                                                                                              // centre
                                                                                                              // de
                                                                                                              // l'écran

        // Terminer le dessin avec SpriteBatch
        batch.end();

        // Mise à jour de la scène et dessin des éléments UI
        stage.act(delta);
        stage.draw();
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
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
        font.dispose();

    }

    public Main getGame() {
        return game;
    }
}
