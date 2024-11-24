package io.github.spaceSurvivor.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Skin skin;

    private BitmapFont MyFont;
    private Label VolumLabel;
    private CheckBox fullScreenCheckBox;
    private Label CheckboxLabel;


    public OptionScreen(Main game) {
        this.game = game;
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        backgroundOption = new Texture("Background/Option.png");

        MyFont = new BitmapFont(Gdx.files.internal("fonts/MyFont.fnt"));
        this.skin = new Skin();
        TextureAtlas Atlas = new TextureAtlas(Gdx.files.internal("Skin/Star-soldier/star-soldier-ui.atlas"));
        skin.addRegions(Atlas);
        skin.load(Gdx.files.internal("Skin/Star-soldier/star-soldier-ui.json"));

        //CHECKBOX
        Texture checkOn = new Texture(Gdx.files.internal("checkbox/on.png"));
        Texture checkOff = new Texture(Gdx.files.internal("checkbox/off.png"));
        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();

        checkBoxStyle.checkboxOn = new TextureRegionDrawable(new TextureRegion(checkOn));
        checkBoxStyle.checkboxOff = new TextureRegionDrawable(new TextureRegion(checkOff));
        checkBoxStyle.font = MyFont;

        fullScreenCheckBox = new CheckBox("", checkBoxStyle);

        fullScreenCheckBox.setChecked(Gdx.graphics.isFullscreen());
        fullScreenCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (fullScreenCheckBox.isChecked()) {
                    game.setFullScreen(); // Appel à la méthode pour activer le plein écran
                } else {
                    game.setWindowedMode(); // Appel à la méthode pour revenir au mode fenêtré
                }
            }
        });

        //SLIDER
        this.audioManager = game.getAudioManager();
        Slider.SliderStyle sliderStyle = skin.get("default-horizontal", Slider.SliderStyle.class);
        Slider slider = new Slider(0, 100, 1, false, sliderStyle);
        slider.setValue(audioManager.getMusicVolume());
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                float volume = slider.getValue();
                audioManager.setMusicVolume(volume);
                Gdx.app.log("OptionScreen", "Volume changé à: " + volume);
            }
        });

        //LABEL
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = MyFont;
        VolumLabel = new Label("Music volume", labelStyle);

        CheckboxLabel = new Label("Fullscreen", labelStyle);


        //BUTTON
        Texture backTexture = new Texture(Gdx.files.internal("buttons/back_up.png"));
        Texture backOverTexture = new Texture(Gdx.files.internal("buttons/back_over.png"));
        Texture backPressedTexture = new Texture(Gdx.files.internal("buttons/back_down.png"));

        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(backTexture);
        backButtonStyle.over = new TextureRegionDrawable(backOverTexture);
        backButtonStyle.down = new TextureRegionDrawable(backPressedTexture);

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
        table.add(CheckboxLabel).padTop(250);
        table.row();
        table.add(fullScreenCheckBox).padTop(30);
        table.row();
        table.add(VolumLabel).padTop(50);
        table.row();
        table.add(slider).width(400).padTop(30);
        table.row();
        table.add(backButton).padTop(150);

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
