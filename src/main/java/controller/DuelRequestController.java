package controller;

import menus.Menu;
import model.User;

import javax.xml.crypto.Data;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DuelRequestController {

    public static ArrayList<DuelController> currentDuels;

    private final static HashMap<User, Menu> oneRoundRequest;
    private final static HashMap<User, Menu> threeRoundRequest;

    static {
        currentDuels = new ArrayList<>();
        oneRoundRequest = new HashMap<>();
        threeRoundRequest = new HashMap<>();
        createDuels();
    }


    public static void addRequest(User user, int rounds, Menu menu) {
        if (rounds == 1) oneRoundRequest.put(user, menu);
        else if (rounds == 3) threeRoundRequest.put(user, menu);
    }

    public static synchronized void createDuels() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    makeAGame(oneRoundRequest);
                    makeAGame(threeRoundRequest);
                }
            }
        });
        thread.start();
    }

    private static void makeAGame(HashMap<User, Menu> requests) {
        if(requests == null) return;
        if (requests.size() >= 2) {
            ArrayList<User> users = new ArrayList<>(requests.keySet());
            User first = users.get(0);
            User second = users.get(1);
            DataOutputStream firstOutput = requests.get(first).dataOutputStream;
            DataOutputStream secondOutput = requests.get(second).dataOutputStream;
            DuelController duelController = new DuelController(first, second);
            try {
                firstOutput.writeUTF("game started");
                firstOutput.flush();
                secondOutput.writeUTF("game started");
                secondOutput.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("started a game between " + first.getUsername() + " and " + second.getUsername());
            requests.remove(first);
            requests.remove(second);
            currentDuels.add(duelController);
        }
    }

}
