package com.mygdx.game.java.controller.servercommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class CommunicateServer {

    static Socket socket;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;
    static int port = 7776;
    static boolean connected = false;
    static Timer timer;
    static TimerTask task;

    public static void setSocket() {
        task = new TimerTask() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost", port);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    setConnected();
                } catch (IOException e) {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
            }
        };

        timer = new Timer("Timer");
        long delay = 5000;
        timer.schedule(task, delay);
    }

    private static void setConnected() {
        task.cancel();
        timer.cancel();
        connected = true;
    }

    public static String write(String message){
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            if (!socket.isConnected()) {
                //TODO we lost our connection, trying...
            }
            return "";
        }
    }

}
