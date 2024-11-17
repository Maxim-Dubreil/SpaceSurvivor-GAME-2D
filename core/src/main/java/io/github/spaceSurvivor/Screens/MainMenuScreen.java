package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
        font = new BitmapFont();
        batch = new SpriteBatch();

        TextButton.TextButtonStyle playButtonStyle = new TextButton.TextButtonStyle();
        playButtonStyle.font = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));

        TextButton.TextButtonStyle optionsButtonStyle = new TextButton.TextButtonStyle();
        optionsButtonStyle.font = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));

        TextButton.TextButtonStyle quitButtonStyle = new TextButton.TextButtonStyle();
        quitButtonStyle.font = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));

        playButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("ui/Transparent.png")));
        playButtonStyle.down = playButtonStyle.up;
        playButtonStyle.over = playButtonStyle.up;

        optionsButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("ui/Transparent.png")));
        optionsButtonStyle.down = optionsButtonStyle.up;
        optionsButtonStyle.over = optionsButtonStyle.up;

        quitButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("ui/Transparent.png")));
        quitButtonStyle.down = quitButtonStyle.up;
        quitButtonStyle.over = quitButtonStyle.up;

        playButtonStyle.fontColor = Color.WHITE;
        playButtonStyle.downFontColor = Color.GRAY;
        playButtonStyle.overFontColor = Color.RED;

        optionsButtonStyle.fontColor = Color.WHITE;
        optionsButtonStyle.downFontColor = Color.GRAY;
        optionsButtonStyle.overFontColor = Color.RED;

        quitButtonStyle.fontColor = Color.WHITE;
        quitButtonStyle.downFontColor = Color.GRAY;
        quitButtonStyle.overFontColor = Color.RED;

        TextButton playButton = new TextButton("Play", playButtonStyle);
        TextButton optionsButton = new TextButton("Options", optionsButtonStyle);
        TextButton quitButton = new TextButton("Quit", quitButtonStyle);

        addHoverEffect(playButton);
        addHoverEffect(optionsButton);
        addHoverEffect(quitButton);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("MainMenuScreen", "Play button clicked, starting game...");
                game.startGame();
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("MainMenuScreen", "Options button clicked, opening options menu...");
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

        table.add(playButton).fillX().uniformX().pad(15).minHeight(50);
        table.row().pad(5, 0, 10, 0);
        table.add(optionsButton).fillX().uniformX().pad(15).minHeight(50);
        table.row();
        table.add(quitButton).fillX().uniformX().pad(15).minHeight(50);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        table.debugAll();

    }

    private void addHoverEffect(final TextButton button) {
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                // Agrandir la police au survol
                button.getLabel().getStyle().font.getData().setScale(1.2f);
                button.getLabel().invalidateHierarchy();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // Réinitialiser la taille de la police
                button.getLabel().getStyle().font.getData().setScale(1.0f);
                button.getLabel().invalidateHierarchy();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.app.log("MainMenuScreen", "Menu displayed");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

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
