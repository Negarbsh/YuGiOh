//3 methods of deck menu main.java.controller tested

import main.java.controller.DeckMenuController;
import main.java.controller.ShopMenuController;
import main.java.model.Deck;
import main.java.model.User;
import main.java.model.card.CardLoader;
import main.java.model.card.PreCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import main.java.view.exceptions.*;

public class DeckMenuTest {  //main.java.controller
    User user;

    @BeforeEach
    public void init() throws AlreadyExistingError {
        user = new User("hasti", "123", "hk");
        DeckMenuController.setUser(user);
        DeckMenuController.createDeck("deck1");
        CardLoader.loadCsv();
//        CardLoader.setCards();
    }

    @Test
    @DisplayName("create deck")
    public void createDeck() throws AlreadyExistingError {
        Assertions.assertNotNull(user.findDeckByName("deck1"));
        Assertions.assertThrows(AlreadyExistingError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DeckMenuController.createDeck("deck1");
            }
        });
    }

    @Test
    @DisplayName("delete deck")
    public void deleteDeck() throws AlreadyExistingError, NotExisting {
        Assertions.assertNull(user.findDeckByName("deck2"));
        Assertions.assertThrows(NotExisting.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DeckMenuController.deleteDeck("deck2");
            }
        });

        DeckMenuController.deleteDeck("deck1");
        Assertions.assertNull(user.findDeckByName("deck1"));
    }

    @Test
    @DisplayName("set active deck")
    public void setDeck() throws AlreadyExistingError, NotExisting {
        Deck deck = user.findDeckByName("deck1");
        DeckMenuController.chooseActiveDeck("deck1");
        Assertions.assertEquals(deck, user.getActiveDeck());
    }

    @Test
    @DisplayName("add card")
    public void addCard() throws AlreadyExistingError, NotExisting, NotEnoughMoney, InvalidName, BeingFull, OccurrenceException, InvalidCommand {
        ShopMenuController.setUser(user);
        Assertions.assertNotNull(PreCard.findCard("Horn Imp"));
        PreCard preCard = PreCard.findCard("Horn Imp");
        user.addPreCardToTreasury(preCard);
        DeckMenuController.addCardToDeck("--card Horn Imp --deck deck1", true);
        DeckMenuController.removeCardFromDeck("--card Horn Imp --deck deck1", true);
        DeckMenuController.showDeck("--deck-name deck1", true);
        DeckMenuController.addCardToDeck("--card Horn Imp --deck deck1", true);
        DeckMenuController.showDeck("--deck-name deck1", true);
    }
}
