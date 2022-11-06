package com.state.menu.ui;

import com.core.Size;
import com.game.settings.AudioSettings;
import com.state.State;
import com.state.game.GameState;
import com.state.game.ui.UIPauseMenu;
import com.state.menu.MenuState;
import com.ui.*;
import com.ui.clickable.UIButton;
import com.ui.clickable.UISlider;
import com.ui.container.UIContainer;
import com.ui.container.VerticalContainer;

//The option menu holding the music slider and sound slider.
public class UIOptionMenu extends UIMenu {

    private final UISlider musicVolumeSlider;
    private final UIText musicVolumeLabel;
    private final UISlider soundVolumeSlider;
    private final UIText soundVolumeLabel;

    public UIOptionMenu(Size windowSize, AudioSettings audioSettings) {
        super(windowSize);

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        musicVolumeSlider = new UISlider(0, 1);
        musicVolumeSlider.setValue(audioSettings.getMusicVolume());
        musicVolumeLabel = new UIText("Music: --");

        soundVolumeSlider = new UISlider(0, 1);
        soundVolumeSlider.setValue(audioSettings.getSoundVolume());
        soundVolumeLabel = new UIText("Sound: --");


        UIContainer labelContainer = new VerticalContainer(windowSize);
        labelContainer.setMargin(new Spacing(0));

        UIContainer contentContainer = new VerticalContainer(windowSize);
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setPadding(new Spacing(10));
        contentContainer.addUIComponent(musicVolumeLabel);
        contentContainer.addUIComponent(musicVolumeSlider);
        contentContainer.addUIComponent(soundVolumeLabel);
        contentContainer.addUIComponent(soundVolumeSlider);

        contentContainer.addUIComponent(new UIButton("Default", (state) -> {

            musicVolumeSlider.setValue(state.getAudioSettings().getDefaultMusicVolume());
            soundVolumeSlider.setValue(state.getAudioSettings().getDefaultSoundVolume());

        }));
        contentContainer.addUIComponent(new UIButton("Back", (state) -> {

            if(state instanceof GameState){

                state.getUiContainers().remove(this);
                state.addUIContainer(new UIPauseMenu(windowSize));

            } else {

                ((MenuState)state).enterMenu(new UIMainMenu(windowSize));

            }

        }));

        addUIComponent(labelContainer);
        addUIComponent(contentContainer);

    }

    @Override
    public void update(State state){
        super.update(state);

        //if the music button is on
        if(state.getAudioSettings().getPlayAudio().getValue()){

            handleVolume(state);

        } else {

            //else if the music button is off, the sliders hold the last value known
            musicVolumeLabel.setText(String.format("Music: %d", Math.round(state.getAudioSettings().getLastMusicVolume() * 100)));
            musicVolumeSlider.setValue(state.getAudioSettings().getLastMusicVolume());
            soundVolumeLabel.setText(String.format("Sound: %d", Math.round(state.getAudioSettings().getLastSoundVolume() * 100)));
            soundVolumeSlider.setValue(state.getAudioSettings().getLastSoundVolume());

        }

    }

    //updates volume of music and sound based on sliders.
    private void handleVolume(State state) {

        state.getAudioSettings().setMusicVolume((float) musicVolumeSlider.getValue());
        musicVolumeLabel.setText(String.format("Music: %d", Math.round(musicVolumeSlider.getValue() * 100)));

        state.getAudioSettings().setSoundVolume((float) soundVolumeSlider.getValue());
        soundVolumeLabel.setText(String.format("Sound: %d", Math.round(soundVolumeSlider.getValue() * 100)));

    }
}
