package com.mygdx.game.java.controller;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mygdx.game.java.controller.servercommunication.CommunicateServer;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.card.PreCardAdapter;
import com.mygdx.game.java.model.forgraphic.Torch;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

//client
public class ScoreBoardMenuController {

    public static void makeScoreBoard(Table table, User user, Skin skin) {
        ArrayList<User> sortedUsers = requestScoreBoard();
        ArrayList<User> onlineUsers = requestOnlineUsers();
        if(sortedUsers == null) sortedUsers = new ArrayList<>();
        if(onlineUsers == null) onlineUsers = new ArrayList<>();
//        ArrayList<User> onlineUsers = new ArrayList<>();
        Table usersTable = new Table();
        int count = 0;
        for (User aUser : sortedUsers) {
            String styleName;
            if (aUser == user && count < 20) styleName = "white";
            else styleName = "default";
            String nickNameText = aUser.getNickName();
            if (onlineUsers.contains(user)) nickNameText += " (online)";
            Label nickname = new Label(nickNameText, skin, styleName);
            Label score = new Label(String.valueOf(aUser.getScore()), skin, styleName);
            Table forUser = new Table();
            Torch firstTorch = new Torch();
            forUser.add(firstTorch).size(40, 25).padBottom(5).padRight(-5);
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

    private static ArrayList<User> requestOnlineUsers() {
        String onlineUsersJson = CommunicateServer.write("scoreBoard - onlineUsers");

        //deserialize:
        Gson gsonExt = null;
        {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(PreCard.class, new PreCardAdapter());
            gsonExt = builder.create();
        }
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        return gsonExt.fromJson(onlineUsersJson, type);
    }

    private static ArrayList<User> requestScoreBoard() {
        String scoreBoardJson = CommunicateServer.write("scoreboard - show");
        System.out.println(scoreBoardJson);
        //deserialize:
        Gson gsonExt = null;
        {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(PreCard.class, new PreCardAdapter());
            gsonExt = builder.create();
        }
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        return gsonExt.fromJson(scoreBoardJson, type);
    }
}