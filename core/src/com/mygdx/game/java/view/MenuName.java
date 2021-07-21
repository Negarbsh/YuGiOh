package com.mygdx.game.java.view;

public enum MenuName {
    LOGIN,
    SHOP,
    PROFILE,
    DECK,
    SCOREBOARD,
    DUEL,
    MAIN;


    public String stringMenu() {
        switch (this) {
            case DECK:
                return "Deck Menu";
            case DUEL:
                return "Duel Menu";
            case MAIN:
                return "Main Menu";
            case SHOP:
                return "Shop Menu";
            case LOGIN:
                return "Login Menu";
            case PROFILE:
                return "Profile Menu";
            case SCOREBOARD:
                return "Scoreboard Menu";
            default:
                return "menu is not here";
        }
    }

    public String MenuHelp() {
        switch (this) {
            case LOGIN:
                return "user create --username <username> --nickname <nickname> --password <password>\n" +
                        "user login --username <username> --password <password>";
            case SHOP:
                return "shop buy <card name>\n" +
                        "shop show --all";
            case PROFILE:
                return "profile change --nickname <nickname>\n" +
                        "profile change --password --current <current password> --new <new password>\n";
            case DECK:
                return "deck create <deck name>\n" +
                        "deck delete <deck name>\n" +
                        "deck set-activate <deck name>\n" +
                        "deck add-card --card <card name> --deck <deck name> --side(optional)\n" +
                        "deck rm-card --card <card name> --deck <deck name> --side(optional)\n" +
                        "deck show --all\n" +
                        "deck show --deck-name <deck name> --side(Opt)\n" +
                        "deck show --cards\n";
            case SCOREBOARD:
                return "scoreboard show";
            case DUEL:
                return "duel --new --second-player <player2 username> --rounds <1/3>\n" +
                        "duel --new --ai --rounds <1/3>\n";
//                        "select <card address>\n" +
//                        "summon\n" +
//                        "set\n" +
//                        "set -- position attack/defense\n" +
//                        "flip-summon\n" +
//                        "attack <number>\n" +
//                        "attack direct\n" +
//                        "activate effect\n" +
//                        "show graveyard\n" +
//                        "card show --selected\n" +
//                        "surrender\n" +
//                        "show board\n";
            case MAIN:
                return "user logout\n" +
                        "menu exit\n" +
                        "menu enter";
            default:
                return "menu is not here";
        }
    }

}
