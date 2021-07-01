package main.java.model.card;

import lombok.Getter;
import main.java.model.GraveYard;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.watchers.Watcher;

import java.util.ArrayList;

@Getter
public abstract class Card {
    public String name;
    public Card instance;
    public PreCard preCardInGeneral;
    public boolean shouldDieAfterActivated = false;
    public ArrayList<Watcher> builtInWatchers;

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

    @Override
    public String toString() {
        return preCardInGeneral.toString();
    }
}
