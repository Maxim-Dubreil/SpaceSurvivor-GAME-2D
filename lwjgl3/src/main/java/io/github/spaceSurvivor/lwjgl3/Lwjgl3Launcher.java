package io.github.spaceSurvivor.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.spaceSurvivor.Main;
import com.badlogic.gdx.Graphics;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired())
            return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
       // return new Lwjgl3Application(new Main(), getDefaultConfiguration());

        Lwjgl3ApplicationConfiguration config = getDefaultConfiguration();
        config.setResizable(false);  // Empêche le redimensionnement de la fenêtre
        //config.setDecorated(false);  // Supprime les décorations (barre de titre et bordures)
        return new Lwjgl3Application(new Main(), config);
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Space Survivor");
        configuration.useVsync(true);

        //Taille par default
        final int widthDefault = 1920;
        final int heightDefault = 1080;
        final float ratio = (float) widthDefault / heightDefault;

        //récupère la taille de l'écran
        Graphics.DisplayMode displayMod = Lwjgl3ApplicationConfiguration.getDisplayMode();
        System.out.println("Display Mode: " + displayMod.width + "x" + displayMod.height + "x");
        int screenWidth = displayMod.width;
        int screenHeight = displayMod.height;
        //Taille de la fenetre
        int windowWidth = widthDefault;
        int windowHeight = heightDefault;

        //Condition pour le redimensionnement
        if (screenWidth < windowWidth || screenHeight < windowHeight) {
            if (screenWidth / (float) screenHeight > ratio) {
                windowHeight = screenHeight;
                windowWidth = (int) (windowHeight * ratio);
            } else {
                windowWidth = screenWidth;
                windowHeight = (int) (windowWidth / ratio);
            }
        }

        configuration.setWindowedMode(windowWidth, windowHeight);


        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
