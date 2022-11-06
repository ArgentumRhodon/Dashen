package com.state.menu;

import com.core.Size;
import com.game.settings.AudioSettings;
import com.input.Input;
import com.map.Map;
import com.state.MusicToggle;
import com.state.State;
import com.state.menu.ui.UIMainMenu;
import com.ui.container.UIContainer;
//The menu state
//Displays the out-of-game menus like options and scores and the main menu
public class MenuState extends State {

    public MenuState(Size windowSize, Input input, AudioSettings audioSettings) {
        super(windowSize, input, audioSettings);

        stateName = "Menu State";

        map = new Map(new Size(13, 10), spriteLibrary);
        initializeUI();

        uiContainers.add(new UIMainMenu(windowSize));

        audioPlayer.playMusic("menuMusic2");


    }

    //sets the current ui to a different container
    public void enterMenu(UIContainer container){

        uiContainers.clear();
        uiContainers.add(container);

        addUIContainer(new MusicToggle(windowSize, getAudioSettings().getPlayAudio()));

    }

}
