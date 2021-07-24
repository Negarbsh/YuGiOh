package com.mygdx.game.java.controller;

import java.io.DataInputStream;
import java.io.IOException;

public class GameBeginningThread extends Thread {
    private final DataInputStream dataInputStream;
    private final LobbyController controller;

    public GameBeginningThread(LobbyController controller, DataInputStream dataInputStream) {
        this.controller = controller;
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        while (controller.shouldWaitForResponse()) {
            try {
                String result = dataInputStream.readUTF();
                if (result.startsWith("game started")) {
                    controller.startTheGame(result);
                    return;
                }
            } catch (IOException ignored) {
            }
        }
    }
}
