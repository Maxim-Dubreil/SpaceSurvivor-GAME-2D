package io.github.spaceSurvivor.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager   {
    //private Music gameMusic;
    private Music menuMusic;
    private float musicVolume = 1f;

    public AudioManager() {

        //Menu
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(musicVolume);
    }

    public void playMenuMusic() {
        stopAllMusic();
        menuMusic.play();
        Gdx.app.log("AudioManager", "Menu music started.");

    }

    public void stopAllMusic() {
        if (menuMusic.isPlaying()) {
            menuMusic.stop();
            Gdx.app.log("AudioManager", "Menu music stopped.");
        }
        //if (gameMusic.isPlaying()) gameMusic.stop();
    }

    public void setMusicVolume (float volume) {
        this.musicVolume = volume;
        menuMusic.setVolume(musicVolume);
        //gameMusic.setVolume(musicVolume);
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void dispose() {
        menuMusic.dispose();
        //gameMusic.dispose();
    }
}
