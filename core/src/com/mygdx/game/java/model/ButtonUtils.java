package com.mygdx.game.java.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.java.model.card.PicState;
import com.mygdx.game.java.model.card.PreCard;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.model.card.PicState;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.Constants;

import java.awt.*;
import java.awt.Container;

public class ButtonUtils {

    public static CustomImageButton createCustomCards(String preCardName) {
        CustomImageButton customImageButton = new CustomImageButton(new TextureRegionDrawable(new TextureRegion(
                PreCard.getCardPic(preCardName))), PreCard.findCard(preCardName));
        return customImageButton;
    }

    public static Table createScroller(int x, int y, int width, int height, Table insideTable, Skin skin) {
        ScrollPane scroller = new ScrollPane(insideTable, skin);
        scroller.setBounds(x, y, width, height + 30);
        scroller.setScrollingDisabled(false, true);
        Table table = new Table();
        table.align(Align.top);
        table.add(scroller).fill().expandX();
        return table;
    }

    public static DeckImageButton createDeckButtons(Deck deck, boolean isActiveDeck, Skin skin) {
        return new DeckImageButton(deck, isActiveDeck, skin);
    }

    public static TextureRegionDrawable makeDrawable(String path) {
        return new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(path))));
    }

    public static Label createMessageBar(String text, BitmapFont font, float scale) {
        if (font == null) return null;
        Label.LabelStyle style = new Label.LabelStyle();
        style.background = makeDrawable("Textures/message-bar.png");
        style.font = font;
        font.setColor(Color.BLACK);
        Label label = new Label(text, style);
        label.setFontScale(scale);
        label.setAlignment(Align.center);
        return label;
    }

    public static TextField createTextField(String text, Skin skin) {
        TextField textField = new TextField(text, skin);
        return textField;
    }

    public static TextButton createTextButton(String text, Skin skin) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setSize(80, 30);
        return textButton;
    }

    public static Label createDuelSideInfoLabel(String text, GameMainClass mainClass) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.background = makeDrawable("Textures/message-bar.png"); //todo change to what you want
        BitmapFont font = mainClass.orangeSkin.getFont("font-title");
        font.setColor(Color.GREEN);
        style.font = font;

        Label label = new Label(text, style);
        label.setWidth(Constants.SIDE_INFO_WIDTH);
        label.setFontScale(0.4f);
        label.setAlignment(Align.center);
        return label;
    }
}
