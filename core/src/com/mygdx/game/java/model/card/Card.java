package com.mygdx.game.java.model.card;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import lombok.Getter;
import com.mygdx.game.java.model.GraveYard;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.watchers.Watcher;

import java.awt.*;
import java.util.ArrayList;

@Getter
public abstract class Card extends Button {
    public String name;
    public Card instance;
    public PreCard preCardInGeneral;
    public boolean shouldDieAfterActivated = false;
    public ArrayList<Watcher> builtInWatchers;
    private CardImageButton cardImageButton;

    {
        builtInWatchers = new ArrayList<>();
    }

    public Card(PreCard preCard) {
        preCardInGeneral = preCard;
        setName(preCard.getName());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void putBuiltInWatchers(CardInUse cardInUse) {
        for (Watcher watcher : builtInWatchers) {
            if (watcher.canPutWatcher()) {
                watcher.putWatcher(cardInUse);
            }
        }
    }

    public void theCardIsBeingDeleted() {
        for (Watcher builtInWatcher : builtInWatchers) {
            builtInWatcher.deleteWatcher();
        }
    }

    public void cardIsBeingSetInCell(CardInUse cardInUse) {
        if (CardLoader.cardsWatchers.containsKey(name)) {
            for (String nameOfWatcher : CardLoader.cardsWatchers.get(name)) {
                builtInWatchers.add(Watcher.createWatcher(nameOfWatcher, cardInUse));
            }
        }

        putBuiltInWatchers(cardInUse);
    }

    public void beVictim(GraveYard graveYard) {
        graveYard.addCard(this);
    }


    public void setCardImageButton(CardImageButton cardImageButton) {
        this.cardImageButton = cardImageButton;
    }

    @Override
    public String toString() {
        return preCardInGeneral.toString();
    }
}
