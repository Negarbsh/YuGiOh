package com.mygdx.game.java.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.CardLoader;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.card.PreCardAdapter;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHandler {
    public static void loadThings() {
        CardLoader.loadCsv();
        loadUsers();
        loadAssets();
    }

    private static void loadAssets() {
        PreCard.setCardsPictures();
    }

    private static void loadUsers() {
        try {

            Gson gsonExt = null;
            {
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(PreCard.class, new PreCardAdapter());
                gsonExt = builder.create();
            }
            String json = new String(Files.readAllBytes(Paths.get("users.json")));
            Type type = new TypeToken<ArrayList<User>>() {
            }.getType();
            User.setAllUsers(gsonExt.fromJson(json, type));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUsers() {
        try {
            Gson gsonExt = null;
            {
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(PreCard.class, new PreCardAdapter());
                gsonExt = builder.create();
            }
            FileWriter fileWriter = new FileWriter("users.json");
            fileWriter.write(gsonExt.toJson(User.getAllUsers()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
