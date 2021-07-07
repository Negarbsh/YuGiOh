package com.mygdx.game.java.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.mygdx.game.java.view.Menus.ProfileMenu;
import com.mygdx.game.java.view.SuccessMessages;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.model.User;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ProfileMenuController {
    User user;
    ProfileMenu profileMenu;

    public ProfileMenuController(ProfileMenu profileMenu, User user) {
        this.profileMenu = profileMenu;
        this.user = user;
    }

    public void showChangePassDialog() {
        Label prePassLabel = new Label("previous password: ", profileMenu.getMainClass().orangeSkin);
        TextField previousPass = new TextField("", profileMenu.getMainClass().orangeSkin);
        Label newPassLabel = new Label("new password: ", profileMenu.getMainClass().orangeSkin);
        TextField newPass = new TextField("", profileMenu.getMainClass().orangeSkin);
        Dialog dialog = new Dialog("", profileMenu.getMainClass().orangeSkin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    hide();
                    try {
                        changePassword(previousPass.getText(), newPass.getText());
                        profileMenu.getMessageBar().setText(String.format(SuccessMessages.changingSuccessfully, "password"));
                        profileMenu.getMessageBar().setColor(Color.GREEN);
                    } catch (WrongPassword | EqualPasswordException e) {
                        profileMenu.getMessageBar().setText(e.getMessage());
                        profileMenu.getMessageBar().setColor(Color.RED);
                    }
                }
            }
        };
        dialog.getContentTable().defaults().pad(10);
        dialog.getContentTable().add(prePassLabel);
        dialog.getContentTable().add(previousPass).row();
        dialog.getContentTable().add(newPassLabel);
        dialog.getContentTable().add(newPass).row();
        dialog.button("ok", true).setHeight(30);
        dialog.show(profileMenu.getStage());
    }

    private void changePassword(String currentPassword, String newPassword) throws WrongPassword, EqualPasswordException {
        if (user.isPasswordWrong(currentPassword))
            throw new WrongPassword();
        else if (currentPassword.equals(newPassword))
            throw new EqualPasswordException();
        else
            user.changePassword(newPassword);
    }

    public void showChangeNickDialog() {
        Label nicknameLabel = new Label("desired nickname: ", profileMenu.getMainClass().orangeSkin);
        TextField nickname = new TextField("", profileMenu.getMainClass().orangeSkin);
        Dialog dialog = new Dialog("", profileMenu.getMainClass().orangeSkin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    hide();
                    try {
                        changeNickname(nickname.getText());
                        profileMenu.getMessageBar().setText(String.format(SuccessMessages.changingSuccessfully, "nickname"));
                        profileMenu.getMessageBar().setColor(Color.GREEN);
                    } catch (AlreadyExistingError e) {
                        profileMenu.getMessageBar().setText(e.getMessage());
                        profileMenu.getMessageBar().setColor(Color.RED);
                    }
                }
            }
        };
        dialog.getContentTable().defaults().pad(10);
        dialog.getContentTable().add(nicknameLabel).row();
        dialog.getContentTable().add(nickname);
        dialog.button("ok", true).setHeight(30);
        dialog.show(profileMenu.getStage());
    }

    private void changeNickname(String nickname) throws AlreadyExistingError {
        if (User.getUserByNickName(nickname) != null)
            throw new AlreadyExistingError("user", "nickname", nickname);
        else
            user.changeNickname(nickname);
    }
}