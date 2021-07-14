//any input should be handled here, the phase is checked here, and the gamePlay is called from here

package com.mygdx.game.java.controller.game;

import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.model.CardAddress;
import com.mygdx.game.java.model.Deck;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.monster.MonsterManner;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.view.Menus.CoinFlipScreen;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.Menus.MainMenu;
import com.mygdx.game.java.view.Menus.TurnScreen;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.messageviewing.Print;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


@Getter
@Setter
public class DuelMenuController {
    private static boolean isAnyGameRunning = false;

    private boolean isRoundEnded = false;

    private User loggedInUser;

    private User firstUser;
    private User secondUser;

    private final ArrayList<HashMap<User, Integer>> usersLP; //the i th member of list, is a hashmap that shows the lp of each user in the i th round
    private final ArrayList<User> roundsWinner;

    private Phase currentPhase;
    private MainPhaseController mainPhaseController;
    private BattlePhaseController battlePhaseController;
    private StandByPhaseController standByPhaseController;
    private DrawPhaseController drawPhaseController;

    private boolean canHaveBattlePhase = false;

    private RoundController roundController;
    private int numOfRounds;
    boolean hasSecondPlayerTurnStarted = false;

    private TurnScreen turnScreen;
    private boolean isGamePaused = false;
    private GameMainClass gameMainClass;

    //    private boolean specialSelectWaiting = false;
    private boolean waitingToChoosePrey = false;


    {
        this.roundsWinner = new ArrayList<>();
        this.usersLP = new ArrayList<>();
    }

    //todo: made public for test! make it private
    private DuelMenuController(User firstUser, User secondUser, int numOfRounds, GameMainClass gameMainClass) throws NumOfRounds {
        DuelMenu.duelMenuController = this;
        this.gameMainClass = gameMainClass;
        this.loggedInUser = firstUser;
        setFirstUser(firstUser);
        setSecondUser(secondUser);
        setNumOfRounds(numOfRounds);
        for (int i = 0; i < numOfRounds; i++) {
            usersLP.add(new HashMap<>());
        }
    }

    /*initializing the match*/

    public static void startNewDuel(String secondUserName, int numOfRounds, GameMainClass gameMainClass, User firstUser) throws InvalidName, NumOfRounds, InvalidDeck, NoActiveDeck, InvalidThing {
        User secondUser = User.getUserByName(secondUserName);
        if (isGameValid(firstUser, secondUser)) {
            DuelMenuController duelMenuController = new DuelMenuController(firstUser, secondUser, numOfRounds, gameMainClass);
            duelMenuController.startDuel();
        }
    }

    private static boolean isGameValid(User firstUser, User secondUser) throws InvalidName, NoActiveDeck, InvalidDeck, InvalidThing {
        if (firstUser == null || secondUser == null)
            throw new InvalidName("user", "username");
        if (firstUser.equals(secondUser)) throw new InvalidThing("Second username");
        checkValidityOfUser(firstUser);
        checkValidityOfUser(secondUser);
        return true;
    }

    private static void checkValidityOfUser(User user) throws NoActiveDeck, InvalidDeck {
        Deck deck = user.getActiveDeck();
        if (deck == null) throw new NoActiveDeck(user.getUsername());
        if (Deck.isDeckInvalid(deck)) throw new InvalidDeck(user.getUsername());
        ArrayList<PreCard> allCards = new ArrayList<>();
        allCards.addAll(deck.getMainCards());
        allCards.addAll(deck.getSideCards());
        for (PreCard preCard : allCards) {
            if (!user.getCardTreasury().containsKey(preCard.getName()))
                throw new InvalidDeck(user.getUsername());
            if (Collections.frequency(allCards, preCard)
                    > user.getCardTreasury().get(preCard.getName()))
                throw new InvalidDeck(user.getUsername());
        }
    }

    public void endGame(GameMainClass gameMainClass) {
//        surrender();
//        isAnyGameRunning = false;
        User user = loggedInUser;
        gameMainClass.setScreen(new MainMenu(gameMainClass, user));

    }


    /*match actions*/

    private void startDuel() {
        isAnyGameRunning = true;
        playHeadOrTails(gameMainClass);
//        runMatch(false); //todo: it should be removed and called by shima
        //after the head or tails was done, the "runMatch()" function is called with a boolean "should swap users" as the input
    }

    //called after the head or tails
    public void runMatch(boolean shouldSwapUsers) {
        if (shouldSwapUsers) {
            User hold = firstUser;
            firstUser = secondUser;
            secondUser = hold;
        }
        runOneRound(0);
//            if (checkMatchFinished()) break;
//            exchangeCardInDecks(roundController.getCurrentPlayer());
//            exchangeCardInDecks(roundController.getRival());
//        }
//        isAnyGameRunning = false;
//        announceWinnerOfMatch();
    }

    public RoundController getProperRoundController(int roundIndex) {
        return new RoundController(this.firstUser, this.secondUser, this, roundIndex);
    }

    private void runOneRound(int roundIndex) {
        this.roundController = getProperRoundController(roundIndex);
        Watcher.roundController = this.roundController;
        changeTurn(false); //here it actually doesn't change, just starts the first turn
    }

    public void setRoundEnded(boolean isRoundEnded) {
        this.isRoundEnded = isRoundEnded;
        if (isRoundEnded) roundController.announceRoundWinner();
    }

    public void changeTurn(boolean shouldSwap) {
        if (shouldSwap) {
            if (!hasSecondPlayerTurnStarted) {
                hasSecondPlayerTurnStarted = true;
            }
            roundController.swapPlayers();
        }

        this.turnScreen = new TurnScreen(roundController.getCurrentPlayer(), roundController.getRival(), this, gameMainClass);
        gameMainClass.setScreen(turnScreen);
        roundController.setTurnEnded(false);
    }

    private void exchangeCardInDecks(Player player) {
        String answer = DuelMenu.askQuestion("Dear" + player.getName() + "!" +
                " Do you want to exchange any card between side deck and main deck?\n" +
                " (no/ from main to side/ from side to main)");
        try {
            switch (answer) {
                case "no":
                    return;
                case "from main to side":
                    String cardName = DuelMenu.askQuestion("Enter the name of the card.");
                    player.getDeck().exchangeCard(cardName, true);
                    break;
                case "from side to main":
                    cardName = DuelMenu.askQuestion("Enter the name of the card.");
                    player.getDeck().exchangeCard(cardName, false);
                    break;
            }
        } catch (InvalidName | NotExisting | OccurrenceException | BeingFull exception) {
            Print.print(exception.getMessage());
        }
        exchangeCardInDecks(player);
    }

    public void handleAndShowRoundWinner(User winner, User loser, int winnerLP, int loserLP, int winnerScore, int loserScore, int roundIndex) {
        this.usersLP.get(roundIndex).put(winner, winnerLP);
        this.usersLP.get(roundIndex).put(loser, loserLP);
        this.roundsWinner.add(winner);
        turnScreen.showWinnerDialog(winner.getUsername(), winner.getScore(), loserScore, false);
    }

    private void announceWinnerOfMatch() {
        isAnyGameRunning = false;
        int firstUserWins = 0, secondUserWins = 0;
        for (int i = 0; i < this.numOfRounds; i++) {
            if (roundsWinner.get(i) != null) {
                if (roundsWinner.get(i).equals(firstUser)) firstUserWins++;
                else if (roundsWinner.get(i).equals(secondUser)) secondUserWins++;
            }
        }
        User matchWinner, matchLoser;
        if (firstUserWins > secondUserWins) {
            matchWinner = firstUser;
            matchLoser = secondUser;
            handleScoreAndBalance(matchWinner, matchLoser);
            turnScreen.showWinnerDialog(matchWinner.getUsername(), firstUserWins, secondUserWins, true);
        } else {
            matchWinner = secondUser;
            matchLoser = firstUser;
            handleScoreAndBalance(matchWinner, matchLoser);
            turnScreen.showWinnerDialog(matchWinner.getUsername(), secondUserWins, firstUserWins, true);
        }
    }

    private void handleScoreAndBalance(User matchWinner, User matchLoser) {
        matchWinner.increaseScore(1000 * numOfRounds);
        matchLoser.increaseBalance(100 * numOfRounds);
        matchWinner.increaseBalance(1000 * numOfRounds);
        int maxWinnerLP = 0;
        for (int i = 0; i < this.roundController.getRoundIndex(); i++) {
            int roundLP = usersLP.get(i).get(matchWinner);
            if (roundLP > maxWinnerLP) maxWinnerLP = roundLP;
        }
        matchWinner.increaseBalance(numOfRounds * maxWinnerLP);
    }

    private boolean checkMatchFinished() {
        if (this.roundController.getRoundIndex() == numOfRounds - 1) return true;
//        if (this.roundController.getRoundIndex() != 0) return false;
        return this.roundsWinner.get(0).equals(this.roundsWinner.get(1));
    }


    public void playHeadOrTails(GameMainClass gameMainClass) {
        boolean isHead = Math.random() < 0.5;
        CoinFlipScreen coinFlipScreen = new CoinFlipScreen(isHead, gameMainClass, this);
        gameMainClass.setScreen(coinFlipScreen);
    }

    private void setNumOfRounds(int numOfRounds) throws NumOfRounds {
        if (numOfRounds != 1 && numOfRounds != 3) throw new NumOfRounds();
        this.numOfRounds = numOfRounds;
    }

    private void setFirstUser(User user) {
        this.firstUser = user;
    }

    private void setSecondUser(User user) {
        this.secondUser = user;
    }

    /* actions in a round*/

    public static String askQuestion(String questionToAsk) {
        return DuelMenu.askQuestion(questionToAsk);
    }

    public void summonMonster(boolean isFlip) throws WrongPhaseForAction, CantDoActionWithCard, UnableToChangePosition, NoSelectedCard, BeingFull, AlreadyDoneAction, NotEnoughTributes {
        if (isGamePaused) return;
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        if (isFlip) mainPhaseController.flipSummon();
        else mainPhaseController.summonMonster();
    }

    public void setCard() throws WrongPhaseForAction, BeingFull, AlreadyDoneAction, CantDoActionWithCard, NoSelectedCard {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.setCard();
    }

    public void changePosition(boolean isToBeAttackMode) throws WrongPhaseForAction, AlreadyDoneAction, UnableToChangePosition, AlreadyInWantedPosition, NoSelectedCard, CantDoActionWithCard {
        if (isGamePaused) return;
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.changePosition(isToBeAttackMode);
    }

    public void attack(int number) throws WrongPhaseForAction, CardAttackedBeforeExeption, CardCantAttack, NoCardToAttack, NoSelectedCard, InvalidSelection, NoCardFound {
        if (isGamePaused) return;
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();

        battlePhaseController.battleAnnounced(number);

    }

    public void attackDirect() throws WrongPhaseForAction, CardAttackedBeforeExeption, CardCantAttack, CantAttackDirectlyException, NoSelectedCard {
        if (isGamePaused) return;
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();
        battlePhaseController.attackToLifePoint();
    }

    public void activateEffect() throws WrongPhaseForAction, NoSelectedCard, ActivateEffectNotSpell, BeingFull, AlreadyActivatedEffect {
        if (isGamePaused) return;
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.activateEffect(true);
    }

    public void selectCardByAddress(String address) throws InvalidSelection, NoCardFound {
        CardAddress cardAddress = new CardAddress(address);
        roundController.selectCardByAddress(cardAddress.getZoneName(), cardAddress.isForOpponent(), cardAddress.getIndex());
    }

    public void selectCard(Card card) {
        Player viewer = turnScreen.getMyPlayer();
        try {
            roundController.selectCard(card, viewer);
        } catch (InvalidSelection invalidSelection) {
            DuelMenu.showException(invalidSelection);
        }
    }

    public void deselectCard() throws NoSelectedCard {
        if (roundController.isAnyCardSelected()) {
            roundController.deselectCard();
        } else throw new NoSelectedCard();
    }

    public String showGraveYard(boolean ofCurrentPlayer) {
        return roundController.showGraveYard(ofCurrentPlayer);
    }

    public void showCard() throws NoSelectedCard {
        if (roundController != null && roundController.isAnyCardSelected()) {
            Card card = roundController.getSelectedCard();
            if (card == null) throw new NoSelectedCard();
            if (!canSeeCard(roundController.getCurrentPlayer(), card)) {
                Print.print("You can't view this card!");
                return;
            }
            CardInUse cardInUse = roundController.getSelectedCardInUse();
            if (cardInUse == null) throw new NoSelectedCard();

//            MonsterCardInUse monsterCardInUse = (MonsterCardInUse) roundController.getSelectedCardInUse();
            if (cardInUse instanceof MonsterCardInUse) {
                MonsterCardInUse monsterCardInUse = (MonsterCardInUse) cardInUse;
                Monster monster = (Monster) monsterCardInUse.getThisCard();
                if (monster != null) {
                    Print.print(monster.getName() + " - attack : " + monsterCardInUse.getAttack() + " defense : " + monsterCardInUse.getDefense() + "\n\t" + monster.getMyPreCard().getDescription());
                }
            } else Print.print(card.toString());
        } else throw new NoSelectedCard();
    }

    public boolean canSeeCard(Player viewer, Card card) {
        CardInUse cardInUse = getRoundController().findCardsCell(card);
        if (cardInUse == null) {
            return viewer.getHand().doesContainCard(card);
        } else {
            if (cardInUse.getBoard().getOwner() == viewer) return true;
            return cardInUse.isFaceUp();
        }
    }

    public void surrender() {
        roundController.surrender();
    }

    public void nextPhase() {
        if (isGamePaused) return;
        if (currentPhase == null) {
            //means that the phase should be draw phase and it's the first turn of our player
            this.currentPhase = Phase.DRAW;
            this.drawPhaseController = new DrawPhaseController(roundController, true);
            drawPhaseController.run();

        } else {
            this.currentPhase = currentPhase.goToNextGamePhase();
            switch (currentPhase) {
                case DRAW:
                    this.drawPhaseController = new DrawPhaseController(roundController, false);
                case STANDBY:
                    this.standByPhaseController = new StandByPhaseController(roundController);
                    break;
                case MAIN_1:
                    //in case MAIN_2 the controller is the same as MAIN_1
                    this.mainPhaseController = new MainPhaseController(roundController);
                    break;
                case BATTLE:
                    if (!canHaveBattlePhase) {
                        canHaveBattlePhase = true;
                        nextPhase();
                        return;
                    }
                    this.battlePhaseController = new BattlePhaseController(roundController);
                    break;
                case MAIN_2:
                    break;
                case END:
                    roundController.setTurnEnded(true);
                    break;
            }
            roundController.updateAfterChangePhase();
            if (currentPhase == Phase.DRAW) drawPhaseController.run();
        }
    }

    public boolean askToEnterSummon() {
        return DuelMenu.forceGetCommand("summon", "you should ritual summon right now");
    }

    public MonsterManner getRitualManner() {
        String mannerString = DuelMenu.getRitualManner();
        return MonsterManner.getMonsterManner(mannerString);
    }

    public void showBoard() {
        if (roundController != null) {
            roundController.showBoard();
        }
    }

    public void nextRound() {
        if (checkMatchFinished()) announceWinnerOfMatch();
        else {
//            exchangeCardInDecks(roundController.getCurrentPlayer());
//            exchangeCardInDecks(roundController.getRival());
            runOneRound(this.roundController.getRoundIndex() + 1);
        }
    }
}