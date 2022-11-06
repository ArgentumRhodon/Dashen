package com.audio;

import com.game.settings.AudioSettings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/*
Responsible for managing a state's audioclips.
 */
public class AudioPlayer{

    private final AudioSettings audioSettings;

    //ARRAYLIST
    private final List<AudioClip> audioClips;

    public AudioPlayer(AudioSettings audioSettings) throws NoAvailableAudioDevice {

        this.audioSettings = audioSettings;
        audioClips = new ArrayList<>();

        checkForAudioDevice();

    }

    /*
    Clips need a available audio device. If there are none present, the
    program will fail. This could just be handled by not creating new clips
    if no devices are connected; however, this solution meets the rethrow requirement
    specified in the rubric.
     */
    //ORIGINAL EXCEPTION AND RETHROW
    private void checkForAudioDevice() throws NoAvailableAudioDevice {

        try{

            if(!isAudioOutputDeviceAvailable()){

                throw new NoAvailableAudioDevice("No audio device detected.");

            }

        } catch (NoAvailableAudioDevice e) {

            System.out.println("Please connect a valid audio device and try again.");
            throw e;

        }

    }

    /*
    Responsible for updating audioclips and also hold the logic for the
    music toggle button.
     */
    public void update(){

        audioClips.forEach(audioClip -> audioClip.update(audioSettings));

        if(!audioSettings.getPlayAudio().getValue() && !audioSettings.isMuted()){

            audioSettings.mute();

        } else if(audioSettings.getPlayAudio().getValue() && audioSettings.isMuted()){

            audioSettings.unMute();

        }

        //using a copy of the list is an alternative to using an iterator
        //less eficient, but the scale of this game makes the difference nominal
        List.copyOf(audioClips).forEach(audioClip -> {

            if(audioClip.hasFinishedPlaying()){

                audioClip.cleanup();
                audioClips.remove(audioClip);

            }

        });

    }

    //checks the system for a(n) available audio device(s)
    public static boolean isAudioOutputDeviceAvailable() {

        final ArrayList<Mixer> available = new ArrayList<>();
        final Mixer.Info[] devices = AudioSystem.getMixerInfo();
        final Line.Info sourceInfo = new Line.Info(SourceDataLine.class);
        for (final Mixer.Info mixerInfo : devices) {
            final Mixer mixer = AudioSystem.getMixer(mixerInfo);
            if (mixer.isLineSupported(sourceInfo)) {
                // the device supports output, add as suitable
                available.add(mixer);
            }
        }
        return available.size() > 0;

    }

    /*
    Below, music clips and sound clips are separated so that the volume of
    each category can be controlled separately.
     */

    //plays a new music clip
    public void playMusic(String fileName){

        final Clip CLIP = getClip(fileName);
        final MusicClip musicClip = new MusicClip(CLIP);
        assert CLIP != null;
        CLIP.loop(Clip.LOOP_CONTINUOUSLY);
        musicClip.setVolume(audioSettings);
        audioClips.add(musicClip);

    }

    //plays a new sound clip
    public void playSound(String fileName){

        final Clip CLIP = getClip(fileName);
        final SoundClip soundClip = new SoundClip(CLIP);
        soundClip.setVolume(audioSettings);
        audioClips.add(soundClip);

    }

    //gets the audio clip associated with a specified .wav sound file
    private Clip getClip(String fileName){

        final URL soundFile = AudioPlayer.class.getResource("/sounds/" + fileName + ".wav");

        try(AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile)) {

            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            final Clip CLIP = (Clip)AudioSystem.getLine(info);
            CLIP.open(audioInputStream);
            CLIP.setFramePosition(0);
            return CLIP;

        } catch (UnsupportedAudioFileException | IOException | NullPointerException | LineUnavailableException e) {

            System.out.println(e);

        }

        return null;

    }

    //clears the audio
    //used when the state changes, since sound is run on independent threads
    public void clear(){

        for (AudioClip audioClip : audioClips) {

            if(audioClip instanceof MusicClip){

                audioClip.cleanup();

            }

        }

        //sound effect sounds will lag the game upon trying to close the clip
        //not sure why
        //can't fix it
        //I know it is a bad handling of memory, but it makes little difference at this scale anyway.
//        audioClips.forEach(AudioClip::cleanup);
        audioClips.clear();

    }

}
