package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.github.spaceSurvivor.Main;

public class MainMenuScreen implements Screen {

    private final Main game;
    private final Stage stage;
    private final Texture backgroundTexture;

    private final BitmapFont font;
    private final SpriteBatch batch;

    public MainMenuScreen(Main game) {
        Gdx.app.log("MainMenuScreen", "Nouvelle instance de MainMenuScreen créée !");

        this.game = game;
        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture("background.png");

        // Initialisation BitmapFont et SpriteBatch
        font = new BitmapFont();
        batch = new SpriteBatch();

        //Style des boutons
        BitmapFont newFont = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = newFont;

        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("ui/Transparent.png")));
        textButtonStyle.down = textButtonStyle.up;
        textButtonStyle.over = textButtonStyle.up;

        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.downFontColor = Color.GRAY;
        textButtonStyle.overFontColor = Color.RED;

        // Initialisation des boutons
        TextButton playButton = new TextButton("Play", textButtonStyle);
        TextButton optionsButton = new TextButton("Options", textButtonStyle);
        TextButton quitButton = new TextButton("Quit", textButtonStyle);

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
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);

        table.add(playButton).fillX().uniformX().pad(20).minHeight(100).minWidth(300);
        table.row().pad(10, 0, 10, 0);
        table.add(optionsButton).fillX().uniformX().pad(20).minHeight(100).minWidth(300);
        table.row();
        table.add(quitButton).fillX().uniformX().pad(20).minHeight(100).minWidth(300);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.app.log("MainMenuScreen", "Menu displayed");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.setDebugAll(true);


        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();

        batch.begin();
        font.getData().setScale(3f);
        font.draw(batch, "Space Survivor", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 50);
        batch.end();

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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        font.dispose();
    }

    public Main getGame() {
        return game;
    }
}
