package com.mygdx.game.java.controller.game;


import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.mygdx.game.java.model.CardAddress;
import com.mygdx.game.java.model.Enums.ZoneName;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.monster.MonsterCardType;
import com.mygdx.game.java.model.card.monster.MonsterType;
import com.mygdx.game.java.model.card.monster.PreMonsterCard;
import com.mygdx.game.java.model.forgraphic.ButtonUtils;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.Menus.TurnScreen;
import com.mygdx.game.java.view.exceptions.CantDoActionWithCard;
import com.mygdx.game.java.view.exceptions.InvalidSelection;
import com.mygdx.game.java.view.exceptions.NoCardFound;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SelectController {
    private ArrayList<ZoneName> zoneNames;
    private final RoundController roundController;
    private CardType cardType = null;
    private final Player selector;
    int upperLevelBound = 100;
    int lowerLevelBound = 0;
    private ArrayList<MonsterType> monsterTypes;
    private boolean isRitual = false; // used in ritual summon
    private boolean isNormal = false; // it means that the selected card should be a normal monster (it is set in the monsterCardType)

    private Card chosenCard;
    private ArrayList<Card> possibleCards;

    {
        possibleCards = new ArrayList<>();
    }

    public SelectController(ArrayList<ZoneName> zoneNames, RoundController roundController, Player selector) {
        this.zoneNames = zoneNames;
        if (zoneNames.isEmpty()) this.zoneNames = null;
        this.roundController = roundController;
        this.selector = selector;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setBounds(int lowerLevelBound, int upperLevelBound) {
        this.lowerLevelBound = lowerLevelBound;
        this.upperLevelBound = upperLevelBound;
    }

    public void setMonsterTypes(MonsterType[] monsterTypes) {
        this.monsterTypes = new ArrayList<>();
        Collections.addAll(this.monsterTypes, monsterTypes);
        if (monsterTypes.length == 0) this.monsterTypes = null;
        if (this.monsterTypes != null) this.cardType = CardType.MONSTER;
    }

    public void setIfRitual(boolean isRitual) {
        this.isRitual = isRitual;
        if (isRitual) {
            setCardType(CardType.MONSTER);
            isNormal = false;
        }
    }

    public void setIfNormal(boolean isNormal) {
        this.isNormal = true;
        if (isNormal) {
            setCardType(CardType.MONSTER);
            isRitual = false;
        }
    }


    public Card getTheCard(){
        return null;//todo remove it after the problems were fixed. I just added to be able to run!

    }
    //this is the function that should be called if we want to get the card!
    // but before that we should call the function "ask to choose possible card" and pass the method to it
    //index varies between 0 and num of possible choices - 1
    public Card getTheCard(int index) {
        return possibleCards.get(index);
//        while (true) {
//            CardAddress cardAddress = askToChoosePossibleCard();
//            if (cardAddress == null) return null;//means that the process is canceled by user
//            Card toReturn;
//            try {
//                toReturn = getCardByAddress(cardAddress);
//                if (toReturn != null) return toReturn;
//                else throw new InvalidSelection();
//            } catch (InvalidSelection | NoCardFound | CantDoActionWithCard invalidSelection) {
//                DuelMenu.showException(invalidSelection);
//            }
//        }
    }

    public CardInUse getTheCardInUse(){
        return null;//todo remove it after the problems were fixed. I just added to be able to run!
    }
    public CardInUse getTheCardInUse(int index) {
        return roundController.findCardsCell(getTheCard(index));
    }


    //an int is given as the parameter of the method. inside that, we should call the "get the card" or "get the card in use" method
    public void askToChoosePossibleCard(Method method, Object ownerOfMethod) {
//        return DuelMenu.forceGetCardAddress(getPossibleChoices());

        TurnScreen turnScreen = DuelMenu.duelMenuController.getTurnScreen();
        if(turnScreen == null) return;
        ArrayList<ImageButton> options = new ArrayList<>();
        for (Card card : possibleCards) {
//            options.add(new ImageButton);
        }
        turnScreen.showImageButtonDialog("Select Box", "Select your desired card.",options, method, ownerOfMethod);
    }

    //the validity of card is also checked here
    private Card getCardByAddress(CardAddress cardAddress) throws InvalidSelection, NoCardFound, CantDoActionWithCard {
        if (cardAddress == null) return null;
        ZoneName zoneName = cardAddress.getZoneName();
        if (!zoneNames.contains(zoneName)) return null;
        Card toReturn = switchAndFindCard(cardAddress, zoneName);

        /* checking the errors */
        if (cardType != null) {
            if (!toReturn.getPreCardInGeneral().getCardType().equals(cardType)) throw new InvalidSelection();
            if (cardType.equals(CardType.MONSTER)) {
                Monster returningMonster = (Monster) toReturn;
                PreMonsterCard preMonster = (PreMonsterCard) returningMonster.getPreCardInGeneral();
                if (isRitual) {
                    if (!preMonster.getMonsterCardType().equals(MonsterCardType.RITUAL))
                        throw new CantDoActionWithCard("ritual summon");
                }
                if (isNormal) {
                    if (!preMonster.getMonsterCardType().equals(MonsterCardType.NORMAL))
                        throw new CantDoActionWithCard("pay tribute with");
                }
                if (isLevelBoundWrong(returningMonster)) throw new InvalidSelection();
                if (monsterTypes != null && !monsterTypes.isEmpty() && !monsterTypes.contains(preMonster.getMonsterType()))
                    throw new InvalidSelection();
            }
        }
        return toReturn;
    }

    private Card switchAndFindCard(CardAddress cardAddress, ZoneName zoneName) throws InvalidSelection, NoCardFound {
        Card toReturn;
        switch (zoneName) {
            case HAND:
                if (cardAddress.isForOpponent()) throw new InvalidSelection();
                toReturn = selector.getHand().getCardWithNumber(cardAddress.getIndex());
                break;
            case MY_MONSTER_ZONE:
                toReturn = selector.getBoard().getCardInUse(cardAddress.getIndex(), true).getThisCard();
                break;
            case MY_SPELL_ZONE:
                toReturn = selector.getBoard().getCardInUse(cardAddress.getIndex(), false).getThisCard();
                break;
            case MY_FIELD:
                toReturn = selector.getBoard().getFieldCell().getThisCard();
                break;
            case MY_GRAVEYARD:
                toReturn = selector.getBoard().getGraveYard().getCard(cardAddress.getIndex());
                break;
            case RIVAL_MONSTER_ZONE:
                toReturn = roundController.getMyRival(selector).getBoard().getCardInUse(cardAddress.getIndex(), true).getThisCard();
                break;
            case RIVAL_SPELL_ZONE:
                toReturn = roundController.getMyRival(selector).getBoard().getCardInUse(cardAddress.getIndex(), false).getThisCard();
                break;
            case RIVAL_FIELD:
                toReturn = roundController.getMyRival(selector).getBoard().getFieldCell().getThisCard();
                break;
            case RIVAL_GRAVEYARD:
                toReturn = roundController.getMyRival(selector).getBoard().getGraveYard().getCard(cardAddress.getIndex());
                break;
            case MY_DECK:
                toReturn = selector.getDeck().getMainCards().get(cardAddress.getIndex() - 1).newCard();
                //cards in graveyard are pre, so we called newCard();
                break;
            default:
                throw new InvalidSelection();
        }
        return toReturn;
    }

    private void handleExtraFeatures(HashMap<Card, CardAddress> possibleChoices) {
        if (cardType != null) {
            for (Card card : possibleChoices.keySet()) {
                if (!card.getPreCardInGeneral().getCardType().equals(cardType))
                    possibleChoices.remove(card);
                if (cardType == CardType.MONSTER) {
                    PreMonsterCard preMonsterCard = ((Monster) card).getMyPreCard();
                    if (monsterTypes != null && !monsterTypes.isEmpty()) {
                        for (MonsterType monsterType : monsterTypes) {
                            if (preMonsterCard.getMonsterType() != monsterType) possibleChoices.remove(card);
                        }
                    }
                    if (isRitual) {
                        if (preMonsterCard.getMonsterCardType() != MonsterCardType.RITUAL) possibleChoices.remove(card);
                    }
                    if (isNormal) {
                        if (preMonsterCard.getMonsterCardType() != MonsterCardType.NORMAL) possibleChoices.remove(card);
                    }
                    if (isLevelBoundWrong(card)) possibleChoices.remove(card);
                }
            }

        }
    }

    private boolean isLevelBoundWrong(Card card) {
        if (card instanceof Monster) {
            Monster monster = (Monster) card;
            return monster.getLevel() < lowerLevelBound || monster.getLevel() > upperLevelBound;
        }
        return true;
    }


    //When we want to show the user the possible choices for selecting, we use this! (in the method getForcedCardAddress)

    private HashMap<Card, CardAddress> getPossibleChoices() {
        HashMap<Card, CardAddress> possibleChoices = new HashMap<>();
        for (ZoneName zoneName : zoneNames) {
            switch (zoneName) {
                case HAND:
                    getCardAddressesIn(possibleChoices, "--hand ", selector.getHand().getCardsInHand().size());
                    break;
                case MY_MONSTER_ZONE:
                    getCardAddressesIn(possibleChoices, "--monster ", selector.getBoard().getMonsterZone().length);
                    break;
                case MY_SPELL_ZONE:
                    getCardAddressesIn(possibleChoices, "--spell ", selector.getBoard().getSpellTrapZone().length);
                    break;
                case MY_FIELD:
                    getCardAddressesIn(possibleChoices, "--field ", 1);
                    break;
                case MY_GRAVEYARD:
                    getCardAddressesIn(possibleChoices, "--graveYard ", selector.getBoard().getGraveYard().getCardsInGraveYard().size());
                    break;
                case RIVAL_MONSTER_ZONE:
                    getCardAddressesIn(possibleChoices, "--monster --opponent", roundController.getMyRival(selector).getBoard().getMonsterZone().length);
                    break;
                case RIVAL_SPELL_ZONE:
                    getCardAddressesIn(possibleChoices, "--spell --opponent", roundController.getMyRival(selector).getBoard().getSpellTrapZone().length);
                    break;
                case RIVAL_FIELD:
                    getCardAddressesIn(possibleChoices, "--field --opponent ", 1);
                    break;
                case RIVAL_GRAVEYARD:
                    getCardAddressesIn(possibleChoices, "--graveYard --opponent ", roundController.getMyRival(selector).getBoard().getGraveYard().getCardsInGraveYard().size());
                    break;
                case MY_DECK:
                    getCardAddressesIn(possibleChoices, "--deck ", selector.getDeck().getMainCards().size());
                    break;
            }
        }
        handleExtraFeatures(possibleChoices);
        return possibleChoices;
    }

    private void getCardAddressesIn(HashMap<Card, CardAddress> possibleAddresses, String zoneNameString, int sizeOfZone) {
        for (int i = 1; i <= sizeOfZone; i++) {
            try {
                CardAddress cardAddress = new CardAddress(zoneNameString + i);
                possibleAddresses.put(getCardByAddress(cardAddress), cardAddress);
                possibleCards.add(getCardByAddress(cardAddress));
            } catch (InvalidSelection | NoCardFound | CantDoActionWithCard invalidSelection) {
                DuelMenu.showException(invalidSelection);
            }
        }
    }
}
