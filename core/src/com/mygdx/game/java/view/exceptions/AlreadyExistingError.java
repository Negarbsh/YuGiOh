package com.mygdx.game.java.view.exceptions;

public class AlreadyExistingError extends Exception{

    public AlreadyExistingError(String type, String subType, String name) {
        super(String.format("%s with %s %s already exists", type, subType, name));
    }
}
