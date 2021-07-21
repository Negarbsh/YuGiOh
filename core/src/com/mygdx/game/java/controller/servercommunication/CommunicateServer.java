package com.mygdx.game.java.controller.servercommunication;

import com.mygdx.game.java.view.exceptions.MyException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
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

    public static String write(String message) {
        try {
loby            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            if (!socket.isConnected()) {
                //TODO we lost our connection, trying...
            }
            return "";
        }
    }

    public static MyException createANewObject(String[] result) {
        String fullClassName = result[1];
        Object[] initials = new Object[result.length - 2];
        for (int i = 2; i < result.length; i++)
            initials[i - 2] = result[i];

        try {
            Class clazz = Class.forName(fullClassName);
            Class[] paramsTypeArray = new Class[initials.length];
            for (int i = 0; i < initials.length; i++) {
                paramsTypeArray[i] = initials[i].getClass();
            }
            Constructor cons = clazz.getDeclaredConstructor(paramsTypeArray);
            cons.setAccessible(true);

            return (MyException) cons.newInstance(initials);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
