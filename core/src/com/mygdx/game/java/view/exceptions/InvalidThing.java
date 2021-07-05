package com.mygdx.game.java.view.exceptions;

public class InvalidThing extends Exception {
    public InvalidThing(String thing) {
        super(thing + " is invalid!");
    }
}
