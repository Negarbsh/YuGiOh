package main.java.view;

import main.java.controller.FileHandler;

public class Main {
    public static void main(String[] args) {
        FileHandler.loadThings();
        Print.print("Welcome to Yo Gi Oh!");
        Print.print("Enter \"menu help\" whenever needed!");
        Menu.run();
    }
}
