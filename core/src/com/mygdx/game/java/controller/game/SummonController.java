/* summon com.mygdx.game.java.controller gets a preCard, a board, and a cell to put the card in. It checks the summon type, handles the tribute and stuff! */

package com.mygdx.game.java.controller.game;


import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.CardAddress;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.monster.MonsterManner;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

public class SummonController {

    private final Monster monster;
    private static int numOfNormalSummons = 0;
    private final RoundController controller;
    private MonsterCardInUse monsterCardInUse;
    private final Board board;
    private boolean hasExtraSummonPermission = false;
    ArrayList<CardInUse> summonedCards; //it is generated in the main phase calling this class

    public SummonController(MonsterCardInUse monsterCardInUse, Monster monster, RoundController controller, ArrayList<CardInUse> summonedCards) {
        this.monster = monster;
        this.controller = controller;
        this.monsterCardInUse = monsterCardInUse;
        this.board = controller.getCurrentPlayerBoard();
        this.summonedCards = summonedCards;
    }

    public static void resetNumOfNormalSummons() {
        numOfNormalSummons = 0;
    }

    public void normal() throws AlreadyDoneAction, NotEnoughTributes {
        if (numOfNormalSummons != 0) {
            if (!hasExtraSummonPermission || monster.getLevel() >= 5) throw new AlreadyDoneAction("summoned");
            else hasExtraSummonPermission = false;
        }
        if (monster.getLevel() >= 5) summonWithTribute(monster);
        else {
            board.getOwner().getHand().removeCard(monster);
            putMonsterInUse(monster, false, this.monsterCardInUse, this.summonedCards, controller);
            board.getOwner().getHand().removeCard(monster);
        }
        numOfNormalSummons++;
    }

    private void summonWithTribute(Monster monster) throws NotEnoughTributes {
        int tributesNeeded = findNumOfTributes(monster);
        if (board.getNumOfAvailableTributes() < tributesNeeded) {
            throw new NotEnoughTributes();
        }
        for (int i = 0; i < tributesNeeded; i++) {
            String address = DuelMenuController.askQuestion("Enter the index of a card for tribute:");
            try {
                payTributeFromBoard(address);
            } catch (InvalidTributeAddress | InvalidSelection invalidAddress) {
                if (address.equals("cancel")) return;
                DuelMenu.showException(invalidAddress);
                i--;
            }
        }
        monsterCardInUse = (MonsterCardInUse) board.getFirstEmptyCardInUse(true);
        board.getOwner().getHand().removeCard(monster);
        putMonsterInUse(monster, false, this.monsterCardInUse, this.summonedCards, this.controller);
    }

    private static void putMonsterInUse(Monster monster, boolean isSpecial, MonsterCardInUse monsterCardInUse, ArrayList<CardInUse> summonedCards, RoundController roundController) {
        if (monsterCardInUse == null) return;
        monsterCardInUse.setACardInCell(monster);
        monsterCardInUse.summon();
        monsterCardInUse.setInAttackMode(true);
        monsterCardInUse.summonFaceUply();
        if (!isSpecial && summonedCards != null) summonedCards.add(monsterCardInUse);
        roundController.updateBoards();
        new SuccessfulAction("", "summoned");

    }

    private int findNumOfTributes(Monster monster) {
        return monster.getNumOfNeededTributes();
    }

    private void payTributeFromBoard(String address) throws InvalidTributeAddress, InvalidSelection {
        CardAddress cardAddress = new CardAddress(address);
        CardInUse cardInUse = cardAddress.getMonsterCardInUseInAddress(board.getMonsterZone());
        if (cardInUse == null) throw new InvalidTributeAddress();
        if (!(cardInUse.getThisCard() instanceof Monster)) throw new InvalidTributeAddress();
        controller.sendToGraveYard(cardInUse);
        controller.updateBoards();
    }


    //any one who calls this, should remove the monster from the current place
    public static void specialSummon(Monster monster, Player player, RoundController roundController, boolean shouldGetMonsterManner) throws BeingFull {
        if (player == null || monster == null) return;
        Board playerBoard = player.getBoard();
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) playerBoard.getFirstEmptyCardInUse(true);
        if (monsterCardInUse == null) throw new BeingFull("monster card zone");
        putMonsterInUse(monster, true, monsterCardInUse, roundController.getDuelMenuController().getMainPhaseController().getSummonedInThisPhase(), roundController);
        roundController.getDuelMenuController().getMainPhaseController().getSummonedInThisPhase().add(monsterCardInUse);

        if (shouldGetMonsterManner) {
            MonsterManner monsterManner = roundController.getDuelMenuController().getRitualManner();
            switch (monsterManner) {
                case DEFENSIVE_HIDDEN: //it won't happen
                case DEFENSIVE_OCCUPIED:
                    monsterCardInUse.summonFaceUply();
                    monsterCardInUse.setInAttackMode(false);
                    break;

                case OFFENSIVE_OCCUPIED:
                    monsterCardInUse.summonFaceUply();
                    monsterCardInUse.setInAttackMode(true);
                    break;
            }
        }
    }
}

