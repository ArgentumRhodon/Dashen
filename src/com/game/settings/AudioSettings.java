package com.game.settings;

/*
Holds the settings for game audio
 */
public class AudioSettings {

    private final float defaultMusicVolume;
    private final float defaultSoundVolume;
    private float lastMusicVolume, lastSoundVolume;
    private float musicVolume, soundVolume;

    private boolean muted;

    private final Setting<Boolean> playAudio;

    public AudioSettings(Setting<Boolean> playAudio) {

        musicVolume = .8f;
        defaultMusicVolume = musicVolume;

        soundVolume = .9f;
        defaultSoundVolume = soundVolume;

        this.playAudio = playAudio;

    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public float getDefaultMusicVolume() {
        return defaultMusicVolume;
    }

    public float getDefaultSoundVolume() {
        return defaultSoundVolume;
    }

    public Setting<Boolean> getPlayAudio() {
        return playAudio;
    }

    //mutes the game sound
    public void mute(){

        muted = true;

        lastMusicVolume = musicVolume;
        lastSoundVolume = soundVolume;

        musicVolume = 0;
        soundVolume = 0;

    }

    //unmutes game sound
    public void unMute(){

        muted = false;

        musicVolume = lastMusicVolume;
        soundVolume = lastSoundVolume;

    }

    public float getLastMusicVolume() {
        return lastMusicVolume;
    }

    public float getLastSoundVolume() {
        return lastSoundVolume;
    }

    public boolean isMuted() {

        return muted;

    }
}
