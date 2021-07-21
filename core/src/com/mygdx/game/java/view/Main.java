package com.mygdx.game.java.view;

import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.view.messageviewing.Print;

public class Main {
    public static void main(String[] args) {
        FileHandler.loadThings();
        Print.print("Welcome to Yo Gi Oh!");
        Print.print("Enter \"menu help\" whenever needed!");
//        Menu.run();
    }
}
