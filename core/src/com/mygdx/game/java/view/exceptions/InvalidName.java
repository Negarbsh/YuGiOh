package com.mygdx.game.java.view.exceptions;

public class InvalidName extends Exception {
    public InvalidName(String group, String name) {
        super("there is no " + group + "  with this " + name);
    }
}
