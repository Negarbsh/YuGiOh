package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.DeckMenuController;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.MenuName;
import com.mygdx.game.java.view.messageviewing.Print;


public class DeckMenu implements Screen {
//    TextureRegionDrawable background;
    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        final String createDeck = "create ";
        final String deleteDeck = "delete ";
        final String activeDeck = "set-activate ";
        final String addCard = "add-card "; //check to see its side or main deck
        final String showAllDecks = "show --all";
        final String showAllCards = "show --cards";
        final String showDeck = "show "; // conflict with upper ones
        final String removeCard = "deck rm-card "; //check to see its side or main deck
    }

//    public DeckMenu(GameMainClass mainClass, User user) {
//        background = ButtonUtils.makeDrawable("Sleeve/50021.png");
//    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
