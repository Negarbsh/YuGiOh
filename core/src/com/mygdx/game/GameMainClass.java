package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Deck;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Menus.DeckPreview;
import com.mygdx.game.java.view.Menus.DuelScreen;
import com.mygdx.game.java.view.Menus.ShopMenu;
import com.mygdx.game.java.view.exceptions.NumOfRounds;

public class GameMainClass extends Game {
//	SpriteBatch batch;
//	Texture img;

    public Skin orangeSkin;
    public Skin flatEarthSkin;

    public Screen lastScreen;

    @Override
    public void create() {

        flatEarthSkin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        orangeSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
        FileHandler.loadThings();
//		setScreen(new ShopMenu(this, User.getUserByName("ali")));

        preformDuelScreenTest();
//        setScreen(new DeckPreview(this, User.getUserByName("akbar")));

    }

    private void preformDuelScreenTest() {
        User ali = User.getUserByName("ali");
        User akbar = User.getUserByName("akbar");
        Deck alis = new Deck("alis");
        Deck akbars = new Deck("akbars");
        ali.addDeck(alis);
        akbar.addDeck(akbars);

        ali.setActiveDeck(ali.getDecks().get(0));
        akbar.setActiveDeck(akbar.getDecks().get(0));
        DuelMenuController duelMenuController = null;
        try {
            duelMenuController = new DuelMenuController(ali, akbar, 1);
        } catch (NumOfRounds numOfRounds) {
            numOfRounds.printStackTrace();
        }
        try {
            if (duelMenuController != null)
//            duelMenuController.runMatch();
                duelMenuController.setRoundController(duelMenuController.getProperRoundController(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DuelScreen duelScreen = new DuelScreen(duelMenuController.getRoundController().getCurrentPlayer(), duelMenuController.getRoundController().getRival(), duelMenuController, this);
        setScreen(duelScreen);
    }


    @Override
    public void setScreen(Screen screen) {
        if (lastScreen != null) lastScreen.dispose();
        else lastScreen = screen;
        super.setScreen(screen);
    }


    @Override
    public void render() {
        super.render();
//		ScreenUtils.clear(1, 0, 0, 1);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
    }

    @Override
    public void dispose() {
//		batch.dispose();
//		img.dispose();
    }
}
