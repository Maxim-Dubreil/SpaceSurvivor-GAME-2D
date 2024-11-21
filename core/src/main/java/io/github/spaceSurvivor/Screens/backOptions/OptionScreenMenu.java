package io.github.spaceSurvivor.Screens.backOptions;

import io.github.spaceSurvivor.Main;
import io.github.spaceSurvivor.Screens.OptionScreen;

public class OptionScreenMenu extends OptionScreen {
    public OptionScreenMenu(Main game) {
        super(game);
    }

    @Override
    public void backTo() {
        game.MainMenuScreen();
    }
}

