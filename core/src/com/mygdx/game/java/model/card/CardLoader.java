//TODO load handmade cards

package com.mygdx.game.java.model.card;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.mygdx.game.java.model.card.monster.PreMonsterCard;
import com.mygdx.game.java.model.card.spelltrap.PreSpellTrapCard;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class CardLoader {
    public static HashMap<String, ArrayList<String>> cardsWatchers;

    static {
        setCardsWatchers();
    }

    public static void loadCsv() {
        //Monster csv
        //Name,Level,Attribute, Monster Type , Card Type ,Atk,Def,Description,Price
        try (CSVReader csvReader = new CSVReader(new FileReader("Monster.csv"));) {
            String[] values = null;
            csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                new PreMonsterCard(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //SpellTrap csv
        //Name, Type, Icon (Property), Description, Status, Price
        try (CSVReader csvReader = new CSVReader(new FileReader("SpellTrap.csv"))) {
            String[] values = null;
            csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                new PreSpellTrapCard(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCardsWatchers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("cards.json")));
            Type type = new TypeToken<HashMap<String, ArrayList<String>>>() {
            }.getType();
            cardsWatchers = new Gson().fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
