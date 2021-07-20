import menus.Menu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public ClientThread(Socket socket) {
        try {
            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {

        }
    }

    @Override
    public void run() {
        Menu menu = new Menu();
        menu.run(dataInputStream, dataOutputStream);
    }
}
