package io.github.spaceSurvivor.Screens.backOptions;

import io.github.spaceSurvivor.Main;
import io.github.spaceSurvivor.Screens.OptionScreen;
import io.github.spaceSurvivor.Screens.PauseScreen;

public class OptionScreenPause extends OptionScreen {
    private final PauseScreen pauseScreen;
    public OptionScreenPause(Main game, PauseScreen pauseScreen) {
        super(game);
        this.pauseScreen = pauseScreen;
    }

    @Override
    public void backTo() {
    game.setScreen(pauseScreen);
    }
}
