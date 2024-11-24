package io.github.spaceSurvivor.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager   {
    private Music gameMusic;
    private final Music menuMusic;
    private float musicVolume = 0.5f;

    public AudioManager() {

        //Menu
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(musicVolume);

        //Game
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(musicVolume);
    }

    public void playMenuMusic() {
        stopAllMusic();
        menuMusic.play();
        menuMusic.setVolume(musicVolume);
        Gdx.app.log("AudioManager", "Menu music started.");
    }

    public void playGameMusic(){
        stopAllMusic();
        gameMusic.play();
        gameMusic.setVolume(musicVolume);
        Gdx.app.log("AudioManager", "Game music started.");
    }

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

    public void setMusicVolume (float volume) {
        this.musicVolume = volume;
        menuMusic.setVolume(musicVolume);
        gameMusic.setVolume(musicVolume);
        Gdx.app.log("AudioManager", "Volume de la musique mis à jour à: " + musicVolume);  // Log pour vérifier
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void dispose() {
        menuMusic.dispose();
        gameMusic.dispose();
    }
}
