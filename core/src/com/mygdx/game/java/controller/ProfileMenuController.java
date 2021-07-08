package com.mygdx.game.java.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.java.view.Menus.ProfileMenu;
import com.mygdx.game.java.view.SuccessMessages;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.model.User;

public class ProfileMenuController {
    User user;
    ProfileMenu profileMenu;
    int avatarNumber;
    Image avatar;

    public ProfileMenuController(ProfileMenu profileMenu, User user) {
        this.profileMenu = profileMenu;
        this.user = user;
        avatar = new Image();
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

    public void setLabelInfo() {
        profileMenu.getInfo().setText(user.getUsername() + "\n" +
                user.getNickName() + "\n" + user.getScore() + "\n" + user.getBalance());
    }

    public void changeAvatar() {
        avatarNumber = user.getAvatarNum();
        avatar.setDrawable(User.charPhotos.get(avatarNumber));
        Dialog dialog = new Dialog("", profileMenu.getMainClass().orangeSkin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    hide();
                    user.setAvatarNum(avatarNumber);
                    profileMenu.getUserAvatar().setDrawable(User.charPhotos.get(avatarNumber));
                } else
                    hide();
            }
        };
        dialog.setSize(400,300);
        ImageButton nextMap = new ImageButton(profileMenu.getMainClass().orangeSkin,
                "right");
        ImageButton preMap = new ImageButton(profileMenu.getMainClass().orangeSkin,
                "left");
        preMap.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                avatarNumber--;
                if (avatarNumber < 0) avatarNumber += 38;
                System.out.println(avatarNumber);
                changeTemporaryImage();
            }
        });
        nextMap.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                avatarNumber++;
                if (avatarNumber > 37) avatarNumber -= 38;
                changeTemporaryImage();
            }
        });
        dialog.getContentTable().defaults().pad(10);
        dialog.getContentTable().add(preMap);
        dialog.getContentTable().add(avatar);
        dialog.getContentTable().add(nextMap);
        dialog.button("ok", true).setHeight(30);
        dialog.button("cancel", false).setHeight(30);
        dialog.show(profileMenu.getStage());
    }

    private void changeTemporaryImage() {
        avatar.setDrawable(User.charPhotos.get(avatarNumber));
    }
}