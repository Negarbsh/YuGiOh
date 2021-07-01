package com.mygdx.game.java.view.Menus;

import com.mygdx.game.java.controller.ImportExportController;
import com.mygdx.game.java.view.exceptions.InvalidName;
import com.mygdx.game.java.view.messageviewing.Print;

import java.io.IOException;

public class ImportExportMenu {
    public static void checkMenuCommands(String command, boolean isImport) throws InvalidName {
        ImportExportController importExportController = new ImportExportController();
        if (isImport) {
            try {
                importExportController.importCard(command);
            } catch (IOException e) {
                Print.print("File error happened");
            }
        } else {
            try {
                Print.print(importExportController.exportCard(command));
            } catch (IOException e) {
                Print.print("File error happened");
            }
        }
    }
}
