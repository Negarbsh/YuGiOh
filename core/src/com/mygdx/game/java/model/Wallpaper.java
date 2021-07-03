package com.mygdx.game.java.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;

public class Wallpaper extends Actor {
    static HashMap<Integer, Texture> allWallpapers;
    Texture picture;
    float x, y, width, height;
    boolean needColor = false;


    public Wallpaper(int id, float x, float y, float width, float height) {
        if (id == 1)    needColor = true;
        picture = allWallpapers.get(id);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (needColor)  batch.setColor(Color.LIGHT_GRAY);
        batch.draw(picture, x, y, width, height);
    }

    public static void setAllWallpapers() {
        if(allWallpapers == null) {
            allWallpapers = new HashMap<>();
            allWallpapers.put(1, new Texture(Gdx.files.internal("Wallpapers/shop_background.png")));
//            allWallpapers.put(1, new Texture(Gdx.files.internal("wallpapers/wallpaper1.jpg")));
        }
    }
}
