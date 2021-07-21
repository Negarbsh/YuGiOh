package com.mygdx.game.java.controller;

import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Menus.LobbyScreen;

public class LobbyController {
    private final User currentUser;
    private LobbyScreen lobbyScreen;

    public LobbyController(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
