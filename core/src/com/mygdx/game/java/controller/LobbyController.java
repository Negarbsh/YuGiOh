package com.mygdx.game.java.controller;

import com.mygdx.game.java.controller.servercommunication.CommunicateServer;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Menus.LobbyScreen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LobbyController {
    private final User currentUser;
    public LobbyScreen lobbyScreen;
    private boolean shouldWaitForResponse;

    public LobbyController(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void sendGameRequest(int rounds, String rivalNameText) {
        String opponent = rivalNameText;
        if (opponent.equals("random")) opponent = "*unknown";
//        CommunicateServer.writeWithoutWaitingForResponse("duel --new --rounds " + rounds + " --opponent " + opponent);
        CommunicateServer.writeWithoutWaitingForResponse("duel " +  rounds + " " + opponent);

        waitForGameResponse();
    }

    public boolean shouldWaitForResponse(){
        return shouldWaitForResponse;
    }

    private void waitForGameResponse() {
        shouldWaitForResponse = true;
        DataInputStream dataInputStream = CommunicateServer.dataInputStream;
        new GameBeginningThread(this, dataInputStream).start();
    }

    public void stopWaitingForGameResponse() {
        shouldWaitForResponse = false;
    }

    public void startTheGame(String serverResponse) {
        System.out.println("game is started!");
        //todo
    }
}
