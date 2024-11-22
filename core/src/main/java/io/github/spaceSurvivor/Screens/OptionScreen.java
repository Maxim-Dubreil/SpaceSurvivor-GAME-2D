package io.github.spaceSurvivor.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.Screen;
import io.github.spaceSurvivor.Main;
import io.github.spaceSurvivor.managers.AudioManager;

public class OptionScreen implements Screen {
    private final Stage stage;
    public Main game;
    private Texture backgroundOption;
    private final SpriteBatch batch;

    private AudioManager audioManager;
    private Slider volumeSlider;
    private Skin skin;


    public OptionScreen(Main game) {
        this.game = game;
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        backgroundOption = new Texture("Background/Option.png");
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.audioManager = game.getAudioManager();
        this.volumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSlider.setSize(400, 50);

        volumeSlider.setValue(audioManager.getMusicVolume());

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                float volume = volumeSlider.getValue();
                audioManager.setMusicVolume(volume);
                Gdx.app.log("OptionScreen", "Volume changé à: " + volume);
            }
        });

        //BUTTON
        Texture backTexture = new Texture(Gdx.files.internal("buttons/back_up.png"));
        //Texture backOverTexture = new Texture(Gdx.files.internal("buttons/back_over.png"));
        //Texture backPressedTexture = new Texture(Gdx.files.internal("buttons/back_down.png"));

        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(backTexture);
        //backButtonStyle.over = new TextureRegionDrawable(backOverTexture);
        //backButtonStyle.down = new TextureRegionDrawable(backPressedTexture);

        ImageButton backButton = new ImageButton(backButtonStyle);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backTo();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(volumeSlider).padTop(20);
        table.row();
        table.add(backButton).padTop(20);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    public void backTo(){

    }

    @Override
    public void show() {
            Gdx.input.setInputProcessor(stage);
        }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(backgroundOption, 0, 0);
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
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundOption.dispose();
        batch.dispose();
        skin.dispose();
    }
}
