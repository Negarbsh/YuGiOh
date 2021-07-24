package controller;

import model.User;

public class DuelController {
    User firstUser;
    User secondUser;

    public DuelController(User firstUser, User secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }
}
