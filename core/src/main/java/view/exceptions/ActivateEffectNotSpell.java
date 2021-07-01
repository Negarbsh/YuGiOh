package main.java.view.exceptions;

public class ActivateEffectNotSpell extends Exception {
    public ActivateEffectNotSpell() {
        super("activate effect is only for spell cards.");
    }
}
