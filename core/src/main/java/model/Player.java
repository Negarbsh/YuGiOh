package main.java.model;

import main.java.controller.game.RoundController;
import main.java.model.card.PreCard;

public class Player {
    //TODO clone the deck when game is started

    private final String name;
    private int lifePoint;
    private final Board board;
    private final Hand hand;
    private final User owner;
    private final RoundController roundController;
    private Deck deck;


    {
        this.hand = new Hand();
    }

    public Player(User owner, RoundController roundController) {
        this.owner = owner;
        this.name = owner.getNickName();
        this.lifePoint = 8000;
        this.roundController = roundController;
        createDeck();
        this.board = new Board(this, roundController.getDuelMenuController());
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public Board getBoard() {
        return board;
    }

    public Hand getHand() {
        return this.hand;
    }

    public void decreaseLifePoint(int decreasingAmount) {
        this.lifePoint -= decreasingAmount;
        roundController.updateBoards();
    }

    public void createDeck() {
        try {
            this.deck = (Deck) owner.getActiveDeck().clone();

            for (PreCard mainCard : deck.getMainCards()) {
                owner.removeCardFromTreasury(mainCard.getName());
            }

            for (PreCard sideCard : deck.getSideCards()) {
                owner.removeCardFromTreasury(sideCard.getName());
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
