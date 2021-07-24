package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.controller.servercommunication.CommunicateServer;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Menus.CardCreatorMenu;
import com.mygdx.game.java.view.Menus.DuelMenuScreen;
import com.mygdx.game.java.view.Menus.RelatedToMenu;

public class GameMainClass extends Game {

    public Skin orangeSkin;
    public Skin flatEarthSkin;
    public static Skin flatEarthSkin2;
    public static Skin orangeSkin2;
    public Music gameMusic;

    public Screen lastScreen;

    @Override
    public void create() {
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/pain.wav"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.1f);
        gameMusic.play();
        flatEarthSkin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        flatEarthSkin.getFont("font").getData().scale(3f);
        flatEarthSkin.getFont("font").setColor(Color.BLACK);
        orangeSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
        flatEarthSkin2 = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        flatEarthSkin2.getFont("font").getData().setScale(1.5f);
        orangeSkin2 = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
        CommunicateServer.setSocket();

        FileHandler.loadThings();
        CommunicateServer.setSocket();
        setScreen(new RelatedToMenu(this));
    }

    @Override
    public void setScreen(Screen screen) {
        if (lastScreen != null) lastScreen.dispose();
        lastScreen = screen;
        super.setScreen(screen);
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }
}
