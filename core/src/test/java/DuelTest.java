import com.mygdx.game.java.controller.LoginMenuController;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

public class DuelTest {
    public User first, second;
    public DuelMenuController duelMenuController;

    @BeforeEach
    public void initializeGame() {
        first = new User("first", "123", "negar");
        second = new User("second", "qwe", "negarPrime");
        Assertions.assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                LoginMenuController.login("first", "123");
                duelMenuController = DuelMenuController.newDuel("second", 1);
                //todo continue
            }
        });

    }
}
