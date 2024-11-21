package io.github.spaceSurvivor.Screens.backOptions;

import io.github.spaceSurvivor.Main;
import io.github.spaceSurvivor.Screens.GameScreen;
import io.github.spaceSurvivor.Screens.OptionScreen;
import io.github.spaceSurvivor.Screens.PauseScreen;

public class OptionScreenPause extends OptionScreen {
    private GameScreen gameScreen;
    public OptionScreenPause(Main game) {
        super(game);
    }

    @Override
    public void backTo() {
        game.setScreen(new PauseScreen(game, gameScreen));
    }
}
