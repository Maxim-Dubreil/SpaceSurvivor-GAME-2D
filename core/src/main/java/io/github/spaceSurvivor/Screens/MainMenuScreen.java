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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.github.spaceSurvivor.Main;
import io.github.spaceSurvivor.Screens.backOptions.OptionScreenMenu;
import io.github.spaceSurvivor.managers.AudioManager;

public class MainMenuScreen implements Screen {

    private final Main game;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final BitmapFont font;
    private final SpriteBatch batch;
    private AudioManager audioManager;

    public MainMenuScreen(Main game) {
        Gdx.app.log("MainMenuScreen", "New instance of MainMenuScreen created !");

        this.game = game;
        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture("Background/Menu.png");
        font = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));
        batch = new SpriteBatch();
        audioManager = game.getAudioManager();
        audioManager.playMenuMusic();
        Gdx.app.log("AudioManager", "AudioManager initialized and menu music should play.");


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

        Color hoverColor = new Color(82 / 255f, 113 / 255f, 255 / 255f, 1);
        Color clickedColor = new Color(122 / 255f, 151 / 255f, 255 / 255f, 1);

        playButtonStyle.fontColor = Color.WHITE;
        playButtonStyle.downFontColor = clickedColor;
        playButtonStyle.overFontColor = hoverColor;

        optionsButtonStyle.fontColor = Color.WHITE;
        optionsButtonStyle.downFontColor = clickedColor;
        optionsButtonStyle.overFontColor = hoverColor;

        quitButtonStyle.fontColor = Color.WHITE;
        quitButtonStyle.downFontColor = clickedColor;
        quitButtonStyle.overFontColor = hoverColor;

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
                game.setScreen(new OptionScreenMenu(game));
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

        table.add(playButton).fillX().uniformX().pad(20).minHeight(50);
        table.row().pad(15, 0, 10, 0);
        table.add(optionsButton).fillX().uniformX().pad(20).minHeight(50);
        table.row().pad(15, 0, 10, 0);
        table.add(quitButton).fillX().uniformX().pad(20).minHeight(50);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    private void addHoverEffect(final TextButton button) {
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.getLabel().getStyle().font.getData().setScale(1.2f);
                button.getLabel().invalidateHierarchy();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
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
        audioManager.stopAllMusic();

    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        font.dispose();
        audioManager.dispose();


    }

    public Main getGame() {
        return game;
    }
}
