package main.java.controller;

import com.google.gson.Gson;
import main.java.view.exceptions.InvalidName;
import main.java.model.card.CardType;
import main.java.model.card.PreCard;
import main.java.model.card.monster.PreMonsterCard;
import main.java.model.card.spelltrap.PreSpellTrapCard;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;

public class ImportExportController {


    public void importCard(String cardName) throws InvalidName, IOException {

        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new InvalidName("card", "name");

        if (preCard.getCardType() == CardType.MONSTER) {

            PreMonsterCard preMonsterCard = (PreMonsterCard) preCard;
            FileWriter fileWriter = new FileWriter("../../resources/cards/monster/" + cardName + ".json");
            fileWriter.write(new Gson().toJson(preMonsterCard));
            fileWriter.close();

        } else {
            PreSpellTrapCard preSpellTrapCard = (PreSpellTrapCard) preCard;
            FileWriter fileWriter = new FileWriter("../../resources/cards/spell-trap/" + cardName + ".json");
            fileWriter.write(new Gson().toJson(preSpellTrapCard));
            fileWriter.close();
        }

    }

    public String exportCard(String cardName) throws InvalidName, IOException {

        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new InvalidName("card", "name");

        if (preCard.getCardType() == CardType.MONSTER) {
            String json = new String(Files.readAllBytes(
                    Paths.get("../../resources/cards/monster/" + cardName + ".json")));
            PreMonsterCard preMonsterCard = new Gson().fromJson(json, PreMonsterCard.class);
            return preMonsterCard.toString();

        } else {

            String json = new String(Files.readAllBytes(
                    Paths.get("../../resources/cards/spell-trap/" + cardName + ".json")));
            PreSpellTrapCard preSpellTrapCard = new Gson().fromJson(json, PreSpellTrapCard.class);
            return preSpellTrapCard.toString();

        }

    }

    public void importUser(Matcher matcher) {


    }

    public void exportUser(Matcher matcher) {

    }

//    public Matcher getMatcher(String info) {
//        return null;
//    }
//
//    public String processFile(File file) {
//        return null;
//    }
}