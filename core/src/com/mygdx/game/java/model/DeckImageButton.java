package com.mygdx.game.java.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.java.model.card.PicState;
import lombok.Getter;

public class DeckImageButton extends Table {
    static TextureRegionDrawable normalDeck, activeDeck;
    @Getter Deck deck;
    @Getter ImageButton imageButton;
    Label label;
    Skin skin;

    public DeckImageButton(Deck deck, boolean isActiveDeck, Skin skin) {
        this.skin = skin;
        this.deck = deck;
        label = new Label(deck.getName(), skin);
        if (isActiveDeck)   createActive();
        else imageButton = new ImageButton(normalDeck);
        this.add(imageButton).size(PicState.DECK_ICON.width, PicState.DECK_ICON.height).row();
        this.add(label);
    }

    private void createActive() {
        imageButton = new ImageButton(activeDeck);
    }

    public static void setDeckImages() {
        normalDeck = new TextureRegionDrawable(new TextureRegion(new Texture(
                Gdx.files.internal("Items/ItemA6000.dds.png"))));

        activeDeck = new TextureRegionDrawable(new TextureRegion(new Texture(
                Gdx.files.internal("Icons/deck_SN0002.dds.png"))));
    }


    public DeckImageButton clone() {
        return new DeckImageButton(deck,
                imageButton.getImage().getDrawable()==activeDeck, skin);
    }
}
