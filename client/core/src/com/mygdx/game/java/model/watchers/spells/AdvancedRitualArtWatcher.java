package com.mygdx.game.java.model.watchers.spells;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.controller.game.SelectController;
import com.mygdx.game.java.controller.game.SummonController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.Enums.ZoneName;
import com.mygdx.game.java.model.GraveYard;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.exceptions.BeingFull;
import com.mygdx.game.java.view.exceptions.InvalidTributeAddress;

import java.util.ArrayList;

public class AdvancedRitualArtWatcher extends Watcher {
    private SelectController selectControllerForMonster;
    private SelectController selectControllerForTribute;
    private Monster monsterToSummon;

    public AdvancedRitualArtWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                getMonsterToSummon();
//                try {
////                    handleRitualSummon();
//                } catch (BeingFull | InvalidTributeAddress ex) {
//                    DuelMenu.showException(ex);
//                }
            }
        }
    }

    private void handleRitualSummon() throws BeingFull, InvalidTributeAddress {

//        Monster monster = getMonsterToSummon();
//        if (monster == null) return;
//
//        ArrayList<Monster> tributes = getTributes(monster.getLevel());
//        if (tributes == null || tributes.isEmpty()) return;
//
//        isWatcherActivated = true;
//        Player player = this.ownerOfWatcher.ownerOfCard;
//        SummonController.specialSummon(monster, player, player.getBoard().getController().getRoundController(), true);
//        sendToGraveYard(tributes);
    }

    private void sendToGraveYard(ArrayList<Monster> tributes) {
        if (tributes == null) return;
        GraveYard graveYard = this.ownerOfWatcher.getBoard().getGraveYard();
        for (Monster tribute : tributes) {
            tribute.beVictim(graveYard);
        }
        roundController.updateBoards();

    }

    private void getMonsterToSummon() {
        if (ownerOfWatcher.getBoard().getController().askToEnterSummon()) return;

        ArrayList<ZoneName> zoneNames = new ArrayList<>();
        zoneNames.add(ZoneName.HAND);
        selectControllerForMonster = new SelectController(zoneNames, ownerOfWatcher.getBoard().getController().getRoundController(), ownerOfWatcher.getOwnerOfCard());
        selectControllerForMonster.setCardType(CardType.MONSTER);
        selectControllerForMonster.setIfRitual(true);
        try {
            selectControllerForMonster.askToChoosePossibleCard(this.getClass().getMethod("handleSelectAction", int.class), this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//        Card card = selectController.askToChoosePossibleCard();
//        if (card instanceof Monster) return (Monster) card;
//        return null; //it may happen when the process is canceled
    }

    public void handleSelectAction(int choice) throws InvalidTributeAddress, BeingFull {
        Card card = selectControllerForMonster.getTheCard(choice);
        if (!(card instanceof Monster)) return;
        monsterToSummon = (Monster) card;
        getTributesAndContinue(monsterToSummon.getLevel());
        //todo ooooooooooooooooooooooooooooooooooo!
//        ArrayList<Monster> tributes = getTributesAndContinue(monsterToSummon.getLevel());
//        if (tributes == null || tributes.isEmpty()) return;
//
//        isWatcherActivated = true;
//        Player player = this.ownerOfWatcher.ownerOfCard;
//        SummonController.specialSummon(monsterToSummon, player, player.getBoard().getController().getRoundController(), true);
//        sendToGraveYard(tributes);

    }

    private void getTributesAndContinue(int levelsSum) throws InvalidTributeAddress {
        ArrayList<ZoneName> deckZone = new ArrayList<>();
        ArrayList<Monster> tributes = new ArrayList<>();
        deckZone.add(ZoneName.MY_DECK);

        selectControllerForTribute = new SelectController(deckZone, ownerOfWatcher.getBoard().getController().getRoundController(), ownerOfWatcher.ownerOfCard);
        selectControllerForTribute.setCardType(CardType.MONSTER);
        selectControllerForTribute.setIfNormal(true);

//        selectControllerForTribute.askToChoosePossibleCard();
//        tributes.add((Monster) selectControllerForTribute.getTheCard());


//        int numOfTributes;
//
//        String numString = DuelMenu.askQuestion("Enter the number of tributes or enter \"cancel\" to cancel!");
//        while (true) {
//            if (numString.equals("cancel")) return null;
//            try {
//                numOfTributes = Integer.parseInt(numString);
//                break;
//            } catch (Exception e) {
//                numString = DuelMenu.askQuestion("Invalid number. " +
//                        "Enter the number of tributes or enter \"cancel\" to cancel!");
//            }
//        }
//        ArrayList<ZoneName> deckZone = new ArrayList<>();
//        ArrayList<Monster> tributes = new ArrayList<>();
//        deckZone.add(ZoneName.MY_DECK);
//
//
//
//        for (int i = 0; i < numOfTributes; i++) {
//            SelectController selectController = new SelectController(deckZone, ownerOfWatcher.getBoard().getController().getRoundController(), ownerOfWatcher.ownerOfCard);
//            selectController.setCardType(CardType.MONSTER);
//            selectController.setIfNormal(true);
//            tributes.add((Monster) selectController.getTheCard());
//        }
//        if (!areTributesValid(tributes, levelsSum)) {
//            throw new InvalidTributeAddress();
//        }
//        return tributes;

    }

    private boolean areTributesValid(ArrayList<Monster> tributeMonsters, int level) {
        if (tributeMonsters == null) return false;
        ArrayList<Integer> levels = new ArrayList<>();
        for (Monster monster : tributeMonsters) {
            levels.add(monster.getLevel());
        }
        return canFindSubsetOfSum(levels, level);
    }


    private boolean canFindSubsetOfSum(ArrayList<Integer> set, int sum) {
        if (sum < 0) return false;
        if (sum == 0) return true;
        if (set.isEmpty()) return false;
        int element = set.get(0);
        if (element < 0) return false;
        set.remove(element);
        if (canFindSubsetOfSum(set, sum)) return true;
        return canFindSubsetOfSum(set, sum - element);
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }
}
