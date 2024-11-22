package io.github.spaceSurvivor.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.spaceSurvivor.Main;

public class GameOverScreen implements Screen {
    private final Main game;
    private final  Stage stage;
    private final GameScreen gameScreen;
    private final Texture backgroundGameOver;
    private final SpriteBatch batch;

    public GameOverScreen(Main game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        backgroundGameOver = new Texture("Background/GameOver.png");

        //TEXTURES BUTTONS
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


        table.add(yesButton).pad(20);
        table.add(noButton).pad(20);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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
        game.startGame();
    }

    public void returnToMainMenu() {
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
        backgroundGameOver.dispose();
        batch.dispose();
    }
}
