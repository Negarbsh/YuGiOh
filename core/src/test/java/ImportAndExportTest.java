//import com.mygdx.game.java.controller.ImportExportController;
//import com.mygdx.game.java.view.exceptions.InvalidName;
//import com.mygdx.game.java.model.card.CardLoader;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.function.Executable;
//
//import java.io.IOException;
//
//public class ImportAndExportTest {
//
//    @BeforeEach
//    public void init() {
//        CardLoader.loadCsv();
//        CardLoader.setCards();
//    }
//
//    @Test
//    @DisplayName("import wrong card name")
//    public void importCardTest() {
//        Executable firstCommands = new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                ImportExportController.importCard("command knight");
//            }
//        };
//        Assertions.assertThrows(InvalidName.class, firstCommands);
//
//        Executable secondCommand = new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                ImportExportController.importCard("Command Knight");
//                ImportExportController.exportCard("SuijinWatcher");
//            }
//        };
//        Assertions.assertThrows(IOException.class,secondCommand);
//
//    }
//
//    @Test
//    @DisplayName("import and export new card")
//    public void importAndExportNewCardTest() throws InvalidName, IOException {
//        ImportExportController.importCard("Command Knight");
//        String json = ImportExportController.exportCard("Command Knight");
//        Assertions.assertNotNull(json);
//        ImportExportController.deleteCard("Command Knight");
//
//    }
//
//}
