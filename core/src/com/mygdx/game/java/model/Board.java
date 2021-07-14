package com.mygdx.game.java.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.GraveYardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.SpellTrapCardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.spelltrap.SpellTrap;
import com.mygdx.game.java.model.forgraphic.ButtonUtils;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.view.Constants;
import com.mygdx.game.java.view.PositionCalculator;
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
    private GraveYardInUse graveYardInUse;

    //watchers which are only available in areWatchingMe Cards. -> scanner, changeOfHearts
    private ArrayList<Watcher> freeBuiltInWatchers;
    private int additionalAttack;
    private int additionalDefense;
    private Player owner;
    private Phase myPhase;
    private DuelMenuController controller;


    {
        monsterZone = new MonsterCardInUse[5];
        spellTrapZone = new SpellTrapCardInUse[5];
        freeBuiltInWatchers = new ArrayList<>();
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
            monsterZone[i] = new MonsterCardInUse(this, i + 1);
            spellTrapZone[i] = new SpellTrapCardInUse(this, i + 1);
        }
        fieldCell = new SpellTrapCardInUse(this, 0);
    }

    public boolean isMonsterZoneFull() {
        return getFirstEmptyCardInUse(true) == null;
    }

    public boolean doesAnyMonsterExist() {
        for (MonsterCardInUse monsterCardInUse : this.monsterZone) {
            if (monsterCardInUse.thisCard != null) return true;
        }
        return false;
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
        if (fieldCell.thisCard == card)
            return fieldCell;
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


    @Override
    public String toString() {
        if (controller.getRoundController().getCurrentPlayer() == this.owner)
            return myTurnString();
        else
            return rivalTurnString();
    }

    public void addButtonsToStage(Stage stage, boolean amViewer) {

        for (int i = 0; i < 5; i++) {
            ImageButton monsterBtn = monsterZone[i].getImageButtonInUse();
            float[] position = PositionCalculator.getCardInUsePosition(i, true, amViewer);
            monsterBtn.setBounds(position[0], position[1], Constants.CARD_IN_USE_WIDTH, Constants.CARD_IN_USE_HEIGHT);
            stage.addActor(monsterBtn);

            ImageButton spellBtn = spellTrapZone[i].getImageButtonInUse();
            position = PositionCalculator.getCardInUsePosition(i, false, amViewer);
            spellBtn.setBounds(position[0], position[1], Constants.CARD_IN_USE_WIDTH, Constants.CARD_IN_USE_HEIGHT);
            stage.addActor(spellBtn);
        }

        ImageButton fieldBtn = fieldCell.getImageButtonInUse();
        float[] position = PositionCalculator.getFieldPosition(amViewer);
        fieldBtn.setBounds(position[0], position[1], Constants.FIELD_WIDTH, Constants.CARD_IN_USE_HEIGHT);
        stage.addActor(fieldBtn);

        position = PositionCalculator.getGraveYardPosition(amViewer);
        ImageButton graveYardBtn = graveYardInUse.getFirstImageButton();
        graveYardBtn.setBounds(position[0], position[1], Constants.CARD_IN_USE_WIDTH, Constants.CARD_IN_USE_HEIGHT);
        stage.addActor(graveYardBtn);

    }

    public void setFieldImage() {
        String bgName = "Field/";
        if(fieldCell.thisCard == null) return;
        SpellTrap spellTrap = (SpellTrap) fieldCell.thisCard;
        switch (spellTrap.getName().toLowerCase()) {
            case "yami":
                bgName += "fie_umi";
                break;
            case "forest":
                bgName += "fie_gaia";
                break;
            case "closed forest":
                bgName += "fie_mori";
                break;
            case "umiruka":
                bgName += "fie_uni";
        }
        bgName = bgName + ".bmp";
        Drawable drawable = ButtonUtils.makeDrawable(bgName);
        controller.getTurnScreen().setBackGround(drawable);
    }
}

