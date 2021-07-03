package com.mygdx.game.java.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reader {
    //directories within assets that you want a catalog of
    static final String[] directories = {
            "Monsters",
            "SpellTrap"
    };

    public static void figureCatalog(String[] folders) {
        String workingDir = System.getProperty("user.dir");
        for (String dir : folders) {
            File directory = new File(workingDir.replace("\\", "/") + "/core/assets/" + dir);
            File outputFile = new File(directory, "catalog.txt");
            FileUtils.deleteQuietly(outputFile); //delete previous catalog
            File[] files = directory.listFiles();
            try {
                for (int i = 0; i < files.length; i++) {
                    FileUtils.write(outputFile, files[i].getName() + (i == files.length - 1 ? "" : "\n"), true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FileHandle[] readDirectoryCatalog(String directory) {
        String[] fileNames = Gdx.files.internal(directory + "/catalog.txt").readString().split("\n");
        FileHandle[] files = new FileHandle[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            files[i] = Gdx.files.internal(directory + "/" + fileNames[i].replaceAll("\\s{2,}", ""));
        }
        return files;
    }

    public void abc() {
        HashMap<String, ArrayList<String>> cards;
        cards = new HashMap<>();
        //monsters
        cards.put("Command Knight", new ArrayList<String>(java.util.List.of("CommandKnightHolyWatcher", "CommandKnightWatcher")));
        cards.put("Yomi Ship", new ArrayList<String>(java.util.List.of("YomiShipWatcher")));
        cards.put("Suijin", new ArrayList<String>(java.util.List.of("SuijinWatcher")));
        cards.put("Man-Eater Bug", new ArrayList<String>(java.util.List.of("ManEaterWatcher")));
        cards.put("Marshmallon", new ArrayList<String>(java.util.List.of("MarshmallonHolyWatcher", "MarshmallonWatcher")));
        cards.put("Texchanger", new ArrayList<String>(java.util.List.of("TexChangerWatcher")));
        cards.put("The Calculator", new ArrayList<String>(java.util.List.of("TheCalculatorWatcher")));

        //spells
        cards.put("Monster Reborn", new ArrayList<String>(java.util.List.of("MonsterRebornWatcher")));
        cards.put("Raigeki", new ArrayList<String>(java.util.List.of("Raigeki")));
        cards.put("Harpie's Feather Duster", new ArrayList<String>(java.util.List.of("Harpie")));
        cards.put("Dark Hole", new ArrayList<String>(java.util.List.of("Dark Hole")));
        cards.put("Yami", new ArrayList<String>(java.util.List.of("YamiFirst", "YamiSec")));
        cards.put("Forest", new ArrayList<String>(java.util.List.of("Forest")));
        cards.put("Closed Forest", new ArrayList<String>(java.util.List.of("ClosedForestWatcher")));
        cards.put("Umiiruka", new ArrayList<String>(java.util.List.of("Umiiruka")));
        cards.put("Sword of dark destruction", new ArrayList<String>(java.util.List.of("Sword-dark")));
        cards.put("Sword of dark Black Pendant", new ArrayList<String>(java.util.List.of("Black")));
        cards.put("United We Stand", new ArrayList<String>(java.util.List.of("United")));
        cards.put("Magnum Shield", new ArrayList<String>(List.of("Magnum")));
        cards.put("Advanced Ritual Art", new ArrayList<String>(List.of("AdvancedRitualArtWatcher")));

        //traps
        cards.put("Magic Cylinder", new ArrayList<String>(List.of("MagicCylinderWatcher")));
        cards.put("Mirror Force", new ArrayList<String>(List.of("MirrorForceWatcher")));
        cards.put("Trap Hole", new ArrayList<String>(List.of("TrapHoleWatcher")));
        cards.put("Torrential Tribute", new ArrayList<String>(List.of("TorrentialTributeWatcher")));
        cards.put("Time Seal", new ArrayList<String>(List.of("TimeSealWatcher")));
        cards.put("Negate Attack", new ArrayList<String>(List.of("NegateAttackWatcher")));


        FileHandle file = Gdx.files.local("cards.json");
        file.writeString(new Gson().toJson(cards), false);
    }
}
