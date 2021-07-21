package server;

import server.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(7776);
            while (true) {
                Socket socket = server.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
