package com.mygdx.game.java.view.exceptions;

public class CardAttackedBeforeExeption extends Exception {
    public CardAttackedBeforeExeption() {
        super("This card already attacked");
    }
}

