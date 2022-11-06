package com.controller;
//a simple interface defining movement information methods
public interface EntityController {

    boolean isRequestingUp();
    boolean isRequestingRight();
    boolean isRequestingDown();
    boolean isRequestingLeft();
    boolean isRequestingCastSpell();

}
