package com.mygdx.game.java.view.exceptions;


public class BeingFull extends Exception{
    public BeingFull(String name) {
        super(String.format("%s is full", name));
    }
}
