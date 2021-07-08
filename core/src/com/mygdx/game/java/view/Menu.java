package com.mygdx.game.java.view;

import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.view.Menus.*;
import com.mygdx.game.java.view.exceptions.InvalidCommand;
import com.mygdx.game.java.view.exceptions.MenuNavigationError;
import com.mygdx.game.java.view.exceptions.NeedToLogin;
import com.mygdx.game.java.view.exceptions.WrongMenu;
import com.mygdx.game.java.view.messageviewing.Print;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    protected static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        if (input == null) return null;
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
