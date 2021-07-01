package main.java.controller;

import main.java.model.Deck;
import main.java.model.User;
import main.java.model.card.PreCard;
import main.java.view.Print;
import main.java.view.exceptions.*;
import main.java.view.messageviewing.SuccessfulAction;

import java.util.ArrayList;
import java.util.Objects;

public class DeckMenuController {
    private static User user;

    public static void setUser(User user) {
        DeckMenuController.user = user;
    }

    public static void createDeck(String deckName) throws AlreadyExistingError {
        if (user.findDeckByName(deckName) != null)
            throw new AlreadyExistingError("deck", "name", deckName);
        else {
            user.addDeck(new Deck(deckName));
            new SuccessfulAction("deck", "created");
        }
    }

    public static void deleteDeck(String deckName) throws NotExisting {
        Deck targetDeck = user.findDeckByName(deckName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else {
            if (user.getActiveDeck() == targetDeck)
                user.setActiveDeck(null);

            user.removeDeck(targetDeck);
        }
    }

    public static void chooseActiveDeck(String deckName) throws NotExisting {
        Deck targetDeck = user.findDeckByName(deckName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else {
            user.setActiveDeck(targetDeck);
            new SuccessfulAction("deck", "activated");
        }
    }

    public static void addCardToDeck(String command, boolean side) throws NotExisting, BeingFull, OccurrenceException, InvalidCommand {   //if it is side deck the boolean should be true
        ArrayList<String> names = analyseCardCommand(command);
        String cardName = names.get(0);
        String deckName = names.get(1);
        Deck targetDeck = user.findDeckByName(deckName);
        if (!user.getCardTreasury().containsKey(cardName) ||
                user.getCardTreasury().get(cardName) == 0)
            throw new NotExisting("card", cardName);
        else if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else {
            targetDeck.addCard(cardName, side);
        }
    }

    public static void removeCardFromDeck(String command, boolean side) throws NotExisting, InvalidCommand {
        ArrayList<String> names = analyseCardCommand(command);
        String cardName = names.get(0);
        String deckName = names.get(1);
        Deck targetDeck = user.findDeckByName(deckName);
        PreCard targetPreCard = PreCard.findCard(cardName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else if (side && !targetDeck.getSideCards().contains(targetPreCard))
            throw new NotExisting("card", cardName);
        else if (!side && !targetDeck.getMainCards().contains(targetPreCard))
            throw new NotExisting("card", cardName);
        else
            targetDeck.removeCard(targetPreCard, side);
    }

    private static ArrayList<String> analyseCardCommand(String command) throws InvalidCommand {
        ArrayList<String> names = new ArrayList<>();
        String cardName = (RelatedToMenuController.
                getCommandString(command, "--card ([^-]+)"));
        String deckName = (RelatedToMenuController.
                getCommandString(command, "--deck ([^-]+)"));
        if (deckName == null || cardName == null) throw new InvalidCommand();
        cardName = cardName.trim();
        deckName = deckName.trim();
        names.add(cardName);
        names.add(deckName);
        return names;
    }

    public static void showAllDecks() {
        Print.print("Decks:\nActive deck:");
        Deck activeDeck = user.getActiveDeck();
        if (activeDeck != null) {
            Print.print(activeDeck.toString());
        }

        Print.print("Other decks:");
        for (Deck deck : user.getDecks()) {
            if (deck != activeDeck)
                Print.print(deck.toString());
        }
    }

    public static void showDeck(String command, boolean side) throws NotExisting {
        String deckName = Objects.requireNonNull(RelatedToMenuController.
                getCommandString(command, "--deck-name ([^-]+)")).trim();
        Deck targetDeck = user.findDeckByName(deckName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);

        targetDeck.showDeck(side);
    }

    public static void showCards() {
        Print.print(user.getMyCardsForPrint());
    }

}