package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class ServerClass {
    static HashMap<String, User> allOnlineUsers;

    static {
        allOnlineUsers = new HashMap<>();


//        String result = new ObjectMapper()
//                .writer(filters)
//                .writeValueAsString(bean);
    }
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

    public synchronized static String addOnlineUser(User user) {
        String token = UUID.randomUUID().toString();
        allOnlineUsers.put(token, user);
        return token;
    }

    public synchronized static void removeAnOnlineUser(String token) {
        allOnlineUsers.remove(token);
    }
}
