package main.java.controller.game;


import main.java.model.CardAddress;
import main.java.model.Enums.ZoneName;
import main.java.model.Player;
import main.java.model.card.Card;
import main.java.model.card.CardType;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.card.monster.Monster;
import main.java.model.card.monster.MonsterCardType;
import main.java.model.card.monster.MonsterType;
import main.java.model.card.monster.PreMonsterCard;
import main.java.view.Menus.DuelMenu;
import main.java.view.exceptions.CantDoActionWithCard;
import main.java.view.exceptions.InvalidSelection;
import main.java.view.exceptions.NoCardFound;

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

    //this is the function that should be called if we want to get the card!
    public Card getTheCard() {
        while (true) {
            CardAddress cardAddress = getForcedCardAddress();
            if (cardAddress == null) return null;//means that the process is canceled by user
            Card toReturn;
            try {
                toReturn = getCardByAddress(cardAddress);
                if (toReturn != null) return toReturn;
                else throw new InvalidSelection();
            } catch (InvalidSelection | NoCardFound | CantDoActionWithCard invalidSelection) {
                DuelMenu.showException(invalidSelection);
            }
        }
    }

    public CardInUse getTheCardInUse() {
        return roundController.findCardsCell(getTheCard());
    }

    private CardAddress getForcedCardAddress() {
        return DuelMenu.forceGetCardAddress(getPossibleChoices());
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

    private void getCardAddressesIn(HashMap<Card, CardAddress> possibleChoices, String zoneNameString, int sizeOfZone) {
        for (int i = 1; i <= sizeOfZone; i++) {
            try {
                CardAddress cardAddress = new CardAddress(zoneNameString + i);
                possibleChoices.put(getCardByAddress(cardAddress), cardAddress);
            } catch (InvalidSelection | NoCardFound | CantDoActionWithCard invalidSelection) {
                DuelMenu.showException(invalidSelection);
            }
        }
    }
}
