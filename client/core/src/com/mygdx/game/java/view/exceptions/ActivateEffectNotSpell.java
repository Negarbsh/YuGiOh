package com.mygdx.game.java.view.exceptions;

public class ActivateEffectNotSpell extends Exception {
    public ActivateEffectNotSpell() {
        super("Activate effect is only for spell cards.");
    }
}
