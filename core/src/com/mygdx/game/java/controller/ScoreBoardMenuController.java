package com.mygdx.game.java.controller;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.User;

import java.util.ArrayList;

public class ScoreBoardMenuController {
    static TextureRegionDrawable torchPic;

    public static void makeScoreBoard(Table table, User user, Skin skin) {
        ArrayList<User> sortedUsers = User.getScoreBoard();
        Table usersTable = new Table();
        int count = 0;
        for (User aUser : sortedUsers) {
            String styleName;
            if (aUser == user && count < 20) styleName = "white";
            else styleName = "default";
            Label nickname = new Label(aUser.getNickName(), skin, styleName);
            Label score = new Label(String.valueOf(aUser.getScore()), skin, styleName);
            Table forUser = new Table();
            Image firstTorch = new Image(torchPic);
            forUser.add(firstTorch).size(40, 25).padBottom(10).padRight(-5);
            forUser.add(nickname).width(100);
            forUser.add(score).width(100);
            usersTable.add(forUser).padBottom(10).row();
            count++;
        }
        ScrollPane usersScroller = new ScrollPane(usersTable, skin, "no-bg");
        usersScroller.setScrollingDisabled(true, false);
        table.align(Align.top);
        table.add(usersScroller).fill();
    }
}