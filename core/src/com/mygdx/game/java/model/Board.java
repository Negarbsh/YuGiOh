package com.mygdx.game.java.model;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.SpellTrapCardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.spelltrap.SpellTrap;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.view.Constants;
import com.mygdx.game.java.view.exceptions.InvalidSelection;
import com.mygdx.game.java.view.exceptions.NoCardFound;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
public class Board {
    private GraveYard graveYard;

    private MonsterCardInUse[] monsterZone;
    private SpellTrapCardInUse[] spellTrapZone;
    private SpellTrapCardInUse fieldCell;   //TODO !!! check updated with new program

    //watchers which are only available in areWatchingMe Cards. -> scanner, changeOfHearts
    private ArrayList<Watcher> freeBuiltInWatchers;
    private int additionalAttack;
    private int additionalDefense;
    private Player owner;
    private Phase myPhase;
    private DuelMenuController controller;

    private Table table;

    {
        monsterZone = new MonsterCardInUse[5];
        spellTrapZone = new SpellTrapCardInUse[5];
        freeBuiltInWatchers = new ArrayList<>();
        table = new Table();
    }

    public Board(Player owner, DuelMenuController controller) {
        this.owner = owner;
        this.controller = controller;
        newCells();
        graveYard = new GraveYard(this);
    }


    public void update() {
        myPhase = myPhase.goToNextPhase();

        for (Watcher freeWatcher : freeBuiltInWatchers) {
            freeWatcher.update(myPhase);
        }

        for (MonsterCardInUse monsterCardInUse : monsterZone) {
            if (!monsterCardInUse.isCellEmpty()) {
                for (Watcher builtInWatcher : monsterCardInUse.thisCard.builtInWatchers) {
                    builtInWatcher.update(myPhase);
                }
            }
        }

        for (SpellTrapCardInUse spellTrapCardInUse : spellTrapZone) {
            if (!spellTrapCardInUse.isCellEmpty()) {
                for (Watcher builtInWatcher : spellTrapCardInUse.thisCard.builtInWatchers) {
                    builtInWatcher.update(myPhase);
                }
            }
        }

        if (!fieldCell.isCellEmpty()) {
            for (Watcher builtInWatcher : fieldCell.thisCard.builtInWatchers) {
                builtInWatcher.update(myPhase);
            }
        }
    }


    public GraveYard getGraveYard() {
        return graveYard;
    }

    private void newCells() {
        for (int i = 0; i < 5; i++) {
            monsterZone[i] = new MonsterCardInUse(this);
            spellTrapZone[i] = new SpellTrapCardInUse(this);
        }
        fieldCell = new SpellTrapCardInUse(this);
    }

    public boolean isMonsterZoneFull() {
        return getFirstEmptyCardInUse(true) == null;
    }

    public CardInUse getFirstEmptyCardInUse(boolean isMonster) {
        CardInUse[] zone;
        if (isMonster) zone = this.monsterZone;
        else zone = this.spellTrapZone;
        for (CardInUse cardInUse : zone) {
            if (cardInUse.isCellEmpty())
                return cardInUse;
        }
        return null;
    }

    public int getNumOfAvailableTributes() {
        int counter = 0;
        for (MonsterCardInUse monsterCardInUse : this.monsterZone) {
            if (monsterCardInUse.getThisCard() != null) counter++;
        }
        return counter;
    }

    /* used for selecting cards*/
    //the index is between 1 and 5
    public CardInUse getCardInUse(int index, boolean isMonster) throws NoCardFound, InvalidSelection {
        if (index < 1 || index > 5) throw new InvalidSelection();
        CardInUse cardInUse;
        if (isMonster) cardInUse = monsterZone[index - 1];
        else cardInUse = spellTrapZone[index - 1];
        if (cardInUse == null) throw new NoCardFound();
        else return cardInUse;
    }

    public CardInUse getCellOf(Card card) {
        if (card instanceof Monster) {
            for (MonsterCardInUse monsterCardInUse : monsterZone) {
                if (monsterCardInUse.getThisCard().equals(card)) return monsterCardInUse;
            }
        } else if (card instanceof SpellTrap) {
            for (SpellTrapCardInUse spellTrapCardInUse : spellTrapZone) {
                if (spellTrapCardInUse.getThisCard().equals(card)) return spellTrapCardInUse;
            }
        }
        return null;
    }

    public void updateAfterAction() {
        for (MonsterCardInUse monsterCardInUse : monsterZone) {
            monsterCardInUse.updateCard();
        }

        for (SpellTrapCardInUse spellTrapCardInUse : spellTrapZone) {
            spellTrapCardInUse.updateCard();
        }

        fieldCell.updateCard();
    }


    public String myTurnString() {
        StringBuilder myBoard = new StringBuilder();
        String horizontalBoarder = "_".repeat(26);
        myBoard.append("\t").append(horizontalBoarder).append("\n\t");

        if (fieldCell.thisCard == null) myBoard.append("E ");
        else myBoard.append("O ");
        myBoard.append("\t".repeat(6));

        myBoard.append(makeTwoBits(graveYard.getNumOfCards())).append("\t\n\t");

        ArrayList<Integer> priorities = new ArrayList<>(Arrays.asList(5, 3, 1, 2, 4));
        myBoard.append("  \t");
        myBoard.append(makeZone(priorities, monsterZone));
        myBoard.append("\t\n\t  \t");
        myBoard.append(makeZone(priorities, spellTrapZone));


        if (owner.getDeck() != null)
            myBoard.append("\t\n\t").append("\t".repeat(6)).append(makeTwoBits(owner.getDeck().getNumOfMainCards())).append("\n\t\t");
        myBoard.append(owner.getHand().toString()).append("\t\n\n\t\t");
        myBoard.append(owner.getName()).append(" : ").append(owner.getLifePoint());
        myBoard.append("\n\t").append(horizontalBoarder);

        return myBoard.toString();
    }

    public String rivalTurnString() {
        StringBuilder rivalBoard = new StringBuilder();
        String horizontalBoarder = "_".repeat(26);
        rivalBoard.append("\t").append(horizontalBoarder).append("\n\t");
        rivalBoard.append("\t").append(owner.getName()).append(" : ").append(owner.getLifePoint()).append("\n\t\t");
        rivalBoard.append(owner.getHand().toString()).append("\n\t");
        if (owner.getDeck() != null)
            rivalBoard.append(makeTwoBits(owner.getDeck().getNumOfMainCards())).append("\n\t");

        ArrayList<Integer> priorities = new ArrayList<>(Arrays.asList(4, 2, 1, 3, 5));
        rivalBoard.append("  \t");
        rivalBoard.append(makeZone(priorities, spellTrapZone));
        rivalBoard.append("\t\n\t  \t");
        rivalBoard.append(makeZone(priorities, monsterZone));

        rivalBoard.append("\n\t");
        rivalBoard.append(makeTwoBits(graveYard.getNumOfCards())).append("\t".repeat(6));
        rivalBoard.append(fieldCell.toString());
        rivalBoard.append("\n\t").append(horizontalBoarder);

        return rivalBoard.toString();
    }

    private String makeTwoBits(int number) {
        String toReturn = String.valueOf(number);
        if (number < 10) toReturn = "0" + number;
        return toReturn;
    }

    private String makeZone(ArrayList<Integer> priorities, CardInUse[] zoneCards) {
        String toReturn = "";
        for (Integer priority : priorities) {
            toReturn = toReturn.concat(zoneCards[priority - 1].toString()) + "\t";
        }
        return toReturn;
    }


    //  This function is called when we want to show the board for the first time in the duelScreen.
    public void setupEntities(boolean isMyOwnerCurrent) {
        table.align(Align.center);
        table.setX(Constants.BOARDS_X);
        table.setWidth(Constants.BOARD_WIDTH);
        table.setHeight(Constants.BOARD_HEIGHT);
        if (isMyOwnerCurrent) { //todo clean code
            table.add(fieldCell.getImageButtonInUse()).padRight(Constants.BOARD_CELLS_GAP);
            for (int i = 0; i < 5; i++) {
                table.add(monsterZone[i].getImageButtonInUse()).width(Constants.CARD_IN_USE_WIDTH).height(Constants.CARD_IN_USE_HEIGHT).padRight(Constants.BOARD_CELLS_GAP);
            }
            table.add(graveYard.getImageButton());
            table.row().pad(Constants.BOARD_ZONES_GAP);
            for (int i = 0; i < 5; i++) {
                table.add(spellTrapZone[i].getImageButtonInUse()).width(Constants.CARD_IN_USE_WIDTH).height(Constants.CARD_IN_USE_HEIGHT).padRight(Constants.BOARD_CELLS_GAP);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                table.add(spellTrapZone[i].getImageButtonInUse()).width(Constants.CARD_IN_USE_WIDTH).height(Constants.CARD_IN_USE_HEIGHT).padRight(Constants.BOARD_CELLS_GAP);
            }
            table.row().pad(Constants.BOARD_ZONES_GAP);
            table.add(fieldCell.getImageButtonInUse()).padRight(Constants.BOARD_CELLS_GAP);
            for (int i = 0; i < 5; i++) {
                table.add(monsterZone[i].getImageButtonInUse()).width(Constants.CARD_IN_USE_WIDTH).height(Constants.CARD_IN_USE_HEIGHT).padRight(Constants.BOARD_CELLS_GAP);
            }
            table.add(graveYard.getImageButton());
        }

    }

    public Table getTable() {
        return table;
    }

    @Override
    public String toString() {
        if (controller.getRoundController().getCurrentPlayer() == this.owner)
            return myTurnString();
        else
            return rivalTurnString();
    }

}

