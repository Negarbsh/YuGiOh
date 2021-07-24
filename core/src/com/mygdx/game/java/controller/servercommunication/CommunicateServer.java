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

    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
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

    public static void writeWithoutWaitingForResponse(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            if (!socket.isConnected()) {
                //TODO we lost our connection, trying...
            }
        }
    }

    public static String write(String message) {
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

    //result[0] = success/ error - result[1] = exception name - exception inputs
    public static MyException createANewObject(String[] result) {
        String fullClassName = result[1];
        Object[] initials = new Object[result.length - 2];
        System.arraycopy(result, 2, initials, 0, result.length - 2);

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
