package com.mygdx.game.java.view.Menus;

import com.mygdx.game.java.controller.DeckMenuController;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.MenuName;
import com.mygdx.game.java.view.Print;

public class DeckMenu {
    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        final String createDeck = "create ";
        final String deleteDeck = "delete ";
        final String activeDeck = "set-activate ";
        final String addCard = "add-card "; //check to see its side or main deck
        final String showAllDecks = "show --all";
        final String showAllCards = "show --cards";
        final String showDeck = "show "; // conflict with upper ones
        final String removeCard = "deck rm-card "; //check to see its side or main deck

        if (RelatedToMenuController.isMenuFalse(MenuName.DECK)) {
            throw new WrongMenu();
        } else try {
            if (command.startsWith(createDeck))
                DeckMenuController.createDeck(command.substring(createDeck.length()));
            else if (command.startsWith(deleteDeck))
                DeckMenuController.deleteDeck(command.substring(deleteDeck.length()));
            else if (command.startsWith(activeDeck))
                DeckMenuController.chooseActiveDeck(command.substring(activeDeck.length()));
            else if (command.startsWith(addCard)) {
                DeckMenuController.addCardToDeck(command.substring(addCard.length()),
                        RelatedToMenuController.getCommandString(command, "--side") != null);
            } else if (command.startsWith(showAllDecks))
                DeckMenuController.showAllDecks();
            else if (command.startsWith(showAllCards))
                DeckMenuController.showCards();
            else if (command.startsWith(showDeck)) {
                DeckMenuController.showDeck(command.substring(showDeck.length()),
                        RelatedToMenuController.getCommandString(command, "--side") != null);
            } else if (command.startsWith(removeCard)) {
                DeckMenuController.removeCardFromDeck(command.substring(removeCard.length()),
                        RelatedToMenuController.getCommandString(command, "--side") != null);
            } else
                throw new InvalidCommand();
        } catch (AlreadyExistingError | NotExisting | BeingFull | OccurrenceException exception) {
            Print.print(exception.getMessage());
        }
    }

}
