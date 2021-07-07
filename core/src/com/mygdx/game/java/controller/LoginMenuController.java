package com.mygdx.game.java.controller;

import com.mygdx.game.java.view.exceptions.AlreadyExistingError;
import com.mygdx.game.java.view.exceptions.EmptyFieldException;
import com.mygdx.game.java.view.exceptions.LoginError;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.messageviewing.SuccessfulAction;

public class LoginMenuController {
    public User user;

    public void createUser(String username, String nickname, String password) throws AlreadyExistingError, EmptyFieldException {
        if (username.equals("") || password.equals("") || nickname.equals(""))
            throw new EmptyFieldException();
        if (hasNoCreatingError(username, nickname)) {
            user = new User(username, password, nickname);
        }
    }

    private boolean hasNoCreatingError(String username, String nickname) throws AlreadyExistingError {
        if (User.getUserByName(username) != null)
            throw new AlreadyExistingError("user", "username", username);
        else if (User.getUserByNickName(nickname) != null)
            throw new AlreadyExistingError("user", "nickname", nickname);
        else return true;
    }

    public void login(String username, String password) throws LoginError {
        if (hasNoLoginError(username, password)) {
            user = User.getUserByName(username);
//            new SuccessfulAction("user", "logged in");
        }
    }

    private boolean hasNoLoginError(String username, String password) throws LoginError {
        User user = User.getUserByName(username);
        if (user == null)
            throw new LoginError();
        else if (user.isPasswordWrong(password))
            throw new LoginError();
        else return true;
    }

}