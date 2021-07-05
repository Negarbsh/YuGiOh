package com.mygdx.game.java.view.exceptions;

public class NoActiveDeck extends Exception {
    public NoActiveDeck(String username) {
        super(username + "  has no active deck");
    }
}
