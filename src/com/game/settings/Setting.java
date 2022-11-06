package com.game.settings;
//a simple generic-holding class for setting definitions and checking
//Not used too much since I ran out of time to delve deeper.

public class Setting<T> {

    private T value;

    public Setting(T value) {

        this.value = value;

    }

    public T getValue() {

        return value;

    }

    public void setValue(T value) {

        this.value = value;

    }

}
