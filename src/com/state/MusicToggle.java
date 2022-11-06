package com.state;

import com.core.Size;
import com.game.settings.Setting;
import com.ui.container.HorizontalContainer;
import com.ui.clickable.UIToggle;
//A container for the music toggle button
public class MusicToggle extends HorizontalContainer {

    public MusicToggle(Size windowSize, Setting<Boolean> playAudio) {
        super(windowSize);

        addUIComponent(new UIToggle("", playAudio));
    
    }

}
