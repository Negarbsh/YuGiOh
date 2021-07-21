package com.mygdx.game.java.controller;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.controller.servercommunication.CommunicateServer;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Menus.LobbyScreen;

import java.util.function.BooleanSupplier;

public class LobbyController {
    private final User currentUser;
    private LobbyScreen lobbyScreen;

    public LobbyController(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void sendGameRequest(int rounds, String rivalNameText) {
        String opponent = rivalNameText;
        if(opponent.equals("")) opponent = "*unknown";
        CommunicateServer.write("duel --new --rounds " + rounds+ " --opponent " + opponent);
    }
}
