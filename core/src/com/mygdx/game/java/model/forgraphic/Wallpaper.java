package com.mygdx.game.java.model.forgraphic;

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
        if (id == 3)    needColor = true;
        picture = allWallpapers.get(id);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.LIGHT_GRAY);
        batch.draw(picture, x, y, width, height);
    }

    public static void setAllWallpapers() {
        if(allWallpapers == null) {
            allWallpapers = new HashMap<>();
            allWallpapers.put(1, new Texture(Gdx.files.internal("Wallpapers/shop-background.png")));
            allWallpapers.put(2, new Texture(Gdx.files.internal("Wallpapers/deck-background.png")));
            allWallpapers.put(3, new Texture(Gdx.files.internal("Wallpapers/duel_background.png")));
            allWallpapers.put(4, new Texture(Gdx.files.internal("Wallpapers/animeSky.jpg")));
//            allWallpapers.put(5, new Texture(Gdx.files.internal("Wallpapers/deck-preview-back.jpg")));
//            allWallpapers.put(6, new Texture(Gdx.files.internal("Wallpapers/deck-preview-back2.jpg")));
            allWallpapers.put(5, new Texture(Gdx.files.internal("Wallpapers/deck-preview-back3.jpg")));
//            allWallpapers.put(6, new Texture(Gdx.files.internal("Wallpapers/deck-preview-back4.jpg")));
        }
    }
}
