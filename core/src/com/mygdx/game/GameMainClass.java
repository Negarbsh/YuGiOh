package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Deck;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Menus.CoinFlipScreen;
import com.mygdx.game.java.view.Menus.DuelMenuScreen;
import com.mygdx.game.java.view.Menus.RelatedToMenu;
import com.mygdx.game.java.view.Menus.ScoreboardMenu;
import com.mygdx.game.java.view.exceptions.NumOfRounds;

import java.util.concurrent.ExecutionException;

public class GameMainClass extends Game {

    public Skin orangeSkin;
    public Skin flatEarthSkin;
    public Skin flatEarthSkin2;
    public Music gameMusic;

    public Screen lastScreen;


    @Override
    public void create() {
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/pain.wav"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.1f);
        gameMusic.play();
        flatEarthSkin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        orangeSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
        flatEarthSkin2 = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        flatEarthSkin2.getFont("font").getData().setScale(1.5f);
        FileHandler.loadThings();

//        setScreen(new ScoreboardMenu(this, User.getUserByName("ali")));
//		setScreen(new ShopMenu(this, User.getUserByName("ali")));

        performCoinScreenTest();
//        preformDuelScreenTest();

//        setScreen(new DeckPreview(this, User.getUserByName("akbar")));
//        setScreen(new CardCreatorMenu(this, User.getUserByName("ali")));
//        setScreen(new LoginMenu(this));
//        setScreen(new SignUpMenu(this));
//        setScreen(new ProfileMenu(this, User.getUserByName("akbar")));
//        setScreen(new RelatedToMenu(this));

    }

    private void performCoinScreenTest(){


        Screen screen = null;
        try {
            screen = new CoinFlipScreen(true,this,
                    new DuelMenuController(User.getUserByName("ali"),User.getUserByName("akbar"),3,this));
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }
        setScreen(screen);


    }


    private void preformDuelScreenTest() {
//        User ali = User.getUserByName("ali");
//        User akbar = User.getUserByName("akbar");
//        Deck alis = new Deck("alis");
//        Deck akbars = new Deck("akbars");
//        ali.addDeck(alis);
//        akbar.addDeck(akbars);
//
//        ali.setActiveDeck(ali.getDecks().get(0));
//        akbar.setActiveDeck(akbar.getDecks().get(0));

        Screen screen = new DuelMenuScreen(User.getUserByName("ali"), this);
        setScreen(screen);
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
};

