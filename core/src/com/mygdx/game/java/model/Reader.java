package com.mygdx.game.java.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

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
//                Util.logError(e);
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
}
