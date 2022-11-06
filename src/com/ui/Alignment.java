package com.ui;
//An alignment for where a UI will appear on the screen

/*
START            CENTER            END





CENTER





END
 */


public class Alignment {

    public enum Position{

        START,CENTER,END

    }

    private final Position HORIZONTAL, VERTICAL;

    public Alignment(Position horizontal, Position vertical) {

        this.VERTICAL = vertical;
        this.HORIZONTAL = horizontal;

    }

    public Position getHORIZONTAL() {

        return HORIZONTAL;

    }

    public Position getVERTICAL() {

        return VERTICAL;

    }

}
