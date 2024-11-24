package io.github.spaceSurvivor.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Manages audio playback for the game, including game music and menu music.
 */
public class AudioManager {

    /** The music track played during the game. */
    private final Music gameMusic;

    /** The music track played in the main menu. */
    private final Music menuMusic;

    /** The volume level for music playback. */
    private float musicVolume = 0.5f;

    /**
     * Initializes the AudioManager by loading music tracks and setting initial
     * volume levels.
     */
    public AudioManager() {
        // Menu music
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(musicVolume);

        // Game music
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(musicVolume);
    }

    /**
     * Plays the menu music track.
     */
    public void playMenuMusic() {
        stopAllMusic();
        menuMusic.play();
        menuMusic.setVolume(musicVolume);
        Gdx.app.log("AudioManager", "Menu music started.");
    }

    /**
     * Plays the game music track.
     */
    public void playGameMusic() {
        stopAllMusic();
        gameMusic.play();
        gameMusic.setVolume(musicVolume);
        Gdx.app.log("AudioManager", "Game music started.");
    }

    /**
     * Stops all currently playing music tracks.
     */
    public void stopAllMusic() {
        if (menuMusic.isPlaying()) {
            menuMusic.stop();
            Gdx.app.log("AudioManager", "Menu music stopped.");
        }
        if (gameMusic.isPlaying()) {
            gameMusic.stop();
            Gdx.app.log("AudioManager", "Game music stopped.");
        }
    }

    /**
     * Sets the volume for music playback.
     *
     * @param volume The desired volume level between 0.0 (mute) and 1.0 (maximum).
     */
    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        menuMusic.setVolume(musicVolume);
        gameMusic.setVolume(musicVolume);
        Gdx.app.log("AudioManager", "Music volume updated to: " + musicVolume);
    }

    /**
     * Retrieves the current music volume level.
     *
     * @return The current volume level.
     */
    public float getMusicVolume() {
        return musicVolume;
    }

    /**
     * Disposes of the music resources when they are no longer needed.
     */
    public void dispose() {
        menuMusic.dispose();
        gameMusic.dispose();
    }
}
