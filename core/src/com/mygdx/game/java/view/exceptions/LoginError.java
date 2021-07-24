package com.mygdx.game.java.view.exceptions;

public class LoginError extends MyException {
    public LoginError() {
        super("Username and password didn't match!");
    }
}
