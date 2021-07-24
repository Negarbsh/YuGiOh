package com.mygdx.game.java.controller;

import com.google.gson.Gson;
import com.mygdx.game.java.controller.servercommunication.CommunicateServer;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.model.User;

import java.util.Objects;

public class LoginMenuController {
    public User user;
    Gson gson = new Gson();

    public void createUser(String username, String nickname, String password) throws MyException, EmptyFieldException {
        if (username.equals("") || password.equals("") || nickname.equals(""))
            throw new EmptyFieldException();
        finalCreating(username, password, nickname);
    }

    private void finalCreating(String username, String password, String nickname) throws MyException {
        String answer = CommunicateServer.write(String.format("user create --username %s --nickname %s --password %s",
                username, nickname, password));
        String[] result = answer.split(" ");
        if (result[0].equals("error")) {
            throw Objects.requireNonNull(ExceptionCreator.createANewObject(result));
        } else {
            user = gson.fromJson(result[2], User.class);
            user.token = result[1];
            System.out.println(user);
        }
    }

    public void login(String username, String password) throws MyException {
        String answer = CommunicateServer.write(String.format("user login --username %s --password %s",
                username, password));
        String[] result = answer.split(" ");
        if (result[0].equals("error")) {
            throw Objects.requireNonNull(ExceptionCreator.createANewObject(result));
        } else {
            user = gson.fromJson(result[2], User.class);
            user.token = result[1];
            System.out.println(user);
        }
    }
}