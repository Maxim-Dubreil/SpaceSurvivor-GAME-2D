package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.spaceSurvivor.Main;
import io.github.spaceSurvivor.Player;

public class GameOverScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final Texture backgroundGameOver;
    private final SpriteBatch batch;

    private Label scoreLabel;
    private BitmapFont font;

    public GameOverScreen(Main game) {
        this.game = game;
        this.stage = new Stage();
        this.batch = new SpriteBatch();

        font = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));
        backgroundGameOver = new Texture("Background/GameOver.png");

        //SCORE LABEL
        System.out.println("Score passed to GameOverScreen: " + Player.score);

        scoreLabel = new Label("Your score: " + Player.score, new Label.LabelStyle(font, Color.YELLOW));

        // TEXTURES BUTTONS
        Texture yesTexture = new Texture(Gdx.files.internal("buttons/yes_up.png"));
        Texture yesOverTexture = new Texture(Gdx.files.internal("buttons/yes_over.png"));
        Texture yesPressedTexture = new Texture(Gdx.files.internal("buttons/yes_down.png"));

        Texture noTexture = new Texture(Gdx.files.internal("buttons/no_up.png"));
        Texture noOverTexture = new Texture(Gdx.files.internal("buttons/no_over.png"));
        Texture noPressedTexture = new Texture(Gdx.files.internal("buttons/no_down.png"));

        ImageButton.ImageButtonStyle yesButtonStyle = new ImageButton.ImageButtonStyle();
        yesButtonStyle.up = new TextureRegionDrawable(new TextureRegion(yesTexture));
        yesButtonStyle.over = new TextureRegionDrawable(new TextureRegion(yesOverTexture));
        yesButtonStyle.down = new TextureRegionDrawable(new TextureRegion(yesPressedTexture));

        ImageButton.ImageButtonStyle noButtonStyle = new ImageButton.ImageButtonStyle();
        noButtonStyle.up = new TextureRegionDrawable(new TextureRegion(noTexture));
        noButtonStyle.over = new TextureRegionDrawable(new TextureRegion(noOverTexture));
        noButtonStyle.down = new TextureRegionDrawable(new TextureRegion(noPressedTexture));

        ImageButton yesButton = new ImageButton(yesButtonStyle);
        ImageButton noButton = new ImageButton(noButtonStyle);

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                replayed();
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                returnToMainMenu();
            }
        });

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(yesButton).pad(20).padTop(200);
        table.add(noButton).pad(20).padTop(200);
        table.row();
        table.add(scoreLabel).pad(20).colspan(2).padTop(80);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        scoreLabel.clearActions();
        scoreLabel.getColor().a = 0;
        scoreLabel.addAction(Actions.fadeIn(2f));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        batch.draw(backgroundGameOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    public void replayed() {
        Player.score = 0;
        game.startGame();
    }

    public void returnToMainMenu() {
        Player.score = 0;
        game.MainMenuScreen();
    }

    @Override
    public void resize(int i, int i1) {

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
        Player.score = 0;
        backgroundGameOver.dispose();
        batch.dispose();

    }
}
