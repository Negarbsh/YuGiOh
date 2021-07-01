package main.java.controller;

import main.java.view.exceptions.*;
import main.java.model.User;

import java.util.Objects;

public class ProfileMenuController {
    private static User user;

    public static void setUser(User user) {
        ProfileMenuController.user = user;
    }

    public static void changeCommands(String command) throws WrongMenu, InvalidCommand, WrongPassword, EqualPasswordException, AlreadyExistingError {
        final String passwordChange = "--password "; // conflict with upper ones
        final String nicknameChange = "--nickname ";

        if (RelatedToMenuController.getCommandString(command, passwordChange) != null)
            changePassword(command.substring(passwordChange.length()));
        else if (RelatedToMenuController.getCommandString(command, nicknameChange) != null)
            changeNickname(command.substring(nicknameChange.length()));
        else
            throw new InvalidCommand();
    }

    private static void changePassword(String command) throws WrongPassword, EqualPasswordException {
        String currentPassword = Objects.requireNonNull(RelatedToMenuController.
                getCommandString(command, "--current ([^-]+)")).trim();
        String newPassword = Objects.requireNonNull(RelatedToMenuController.
                getCommandString(command, "--new ([^-]+)")).trim();
        if (user.isPasswordWrong(currentPassword))
            throw new WrongPassword();
        else if (currentPassword.equals(newPassword))
            throw new EqualPasswordException();
        else
            user.changePassword(newPassword);
    }

    private static void changeNickname(String nickname) throws AlreadyExistingError {
        if (User.getUserByNickName(nickname) != null)
            throw new AlreadyExistingError("user", "nickname", nickname);
        else
            user.changeNickname(nickname);
    }
}