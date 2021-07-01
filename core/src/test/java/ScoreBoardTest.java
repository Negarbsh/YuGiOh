import main.java.controller.ScoreBoardMenuController;
import main.java.model.User;
import main.java.model.card.CardLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {
    @Test
    public void scoreBoardTest() {
        new User("negar", " 123", "bsh");
        new User("mehrdad", " 123", "bsh");
        String scoreBoard = ScoreBoardMenuController.showScoreBoard();
        Assertions.assertEquals(scoreBoard, "1- mehrdad: 0\n1- negar: 0\n");
    }

    @Test
    @DisplayName("load cards")
    public void load() {
        CardLoader.loadCsv();
//        CardLoader.setCards();
    }
}
