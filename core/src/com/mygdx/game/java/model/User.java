
package com.mygdx.game.java.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.view.messageviewing.Print;
import lombok.Setter;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.SuccessMessages;

import java.util.*;

@Setter
public class User implements Comparable<User> {
    private static Random random;
    private final String username;
    private String password;
    private String nickName;
    private int score;
    private final HashMap<String, Integer> cardTreasury;   //shows how many cards do we have of each type
    private final ArrayList<Deck> decks;
    private int balance;
    private int avatarNum;
    private transient Image avatarImage;
    private String activeDeck;
    private static ArrayList<User> allUsers;
    public static HashMap<Integer, TextureRegionDrawable> charPhotos;

    static {
        allUsers = new ArrayList<>();
        charPhotos = new HashMap<>();
        random = new Random();
    }

    {
        cardTreasury = new HashMap<>();
        decks = new ArrayList<>();
    }

    public User(String username, String password, String nickName) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.score = 0;
        this.balance = 10000; //todo I'm not sure!
        avatarNum = random.nextInt(42);
        allUsers.add(this);
        FileHandler.saveUsers();
    }

    public static User getUserByName(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void setAllUsers(ArrayList<User> users) {
        allUsers = users;
    }

    public static User getUserByNickName(String nickName) {
        for (com.mygdx.game.java.model.User user : allUsers) {
            if (user.nickName.equals(nickName)) {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getScoreBoard() {
        Collections.sort(allUsers);
        return allUsers;
    }

    public Image getAvatar() {
        if (avatarImage == null) avatarImage = new Image(charPhotos.get(avatarNum));
        return avatarImage;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public int getScore() {
        return score;
    }

    public HashMap<String, Integer> getCardTreasury() {
        return cardTreasury;
    }

    public Deck findDeckByName(String name) {
        if (name == null)
            return null;

        for (Deck deck : decks) {
            if (deck.equalNames(name))
                return deck;
        }

        return null;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public int getBalance() {
        return balance;
    }

    public Deck getActiveDeck() {
        return findDeckByName(activeDeck);
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeNickname(String newNickname) {
        this.nickName = newNickname;
    }

    public void increaseBalance(int increasingAmount) {
        this.balance += increasingAmount;
    }

    public void decreaseBalance(int decreasingAmount) {
        this.balance -= decreasingAmount;
    }

    public void addPreCardToTreasury(PreCard preCard) {
        if (preCard == null) return;
        if (this.cardTreasury.containsKey(preCard.getName())) {
            cardTreasury.put(preCard.getName(), cardTreasury.get(preCard.getName()) + 1);
        } else {
            this.cardTreasury.put(preCard.getName(), 1);
        }
    }

    public void removeCardFromTreasury(String nameOfCard) {
        int numOfCard = this.cardTreasury.get(nameOfCard) - 1;
        this.cardTreasury.put(nameOfCard, numOfCard);
        if (numOfCard == 0) this.cardTreasury.remove(nameOfCard);
    }

    public void increaseScore(int increasingAmount) {
        this.score += increasingAmount;
    }

    public boolean isPasswordWrong(String password) {
        return !password.equals(this.password);
    }

    public void setActiveDeck(Deck deck) {
        if (this.decks.contains(deck)) {
            this.activeDeck = deck.getName();
        }
    }

    @Override
    public int compareTo(com.mygdx.game.java.model.User otherUser) {
        if (this.score > otherUser.score) return -1;
        if (this.score < otherUser.score) return 1;
        return this.username.compareTo(otherUser.username);
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
        FileHandler.saveUsers();
    }

    public void removeDeck(Deck deck) {
        decks.remove(deck);
        if (this.getActiveDeck() == deck)    activeDeck = null;
        FileHandler.saveUsers();
    }

    public String getMyCardsForPrint() {
        ArrayList<String> myCards = new ArrayList<>(getCardTreasury().keySet());

        if (myCards.isEmpty()) {
            return "Empty!";
        }

        Collections.sort(myCards);
        StringBuilder cards = new StringBuilder();
        for (String cardName : myCards) {
            cards.append(Objects.requireNonNull(PreCard.findCard(cardName)).toString());
        }
        return cards.toString();
    }

}
