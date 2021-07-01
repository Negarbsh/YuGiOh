import com.mygdx.game.java.controller.ShopMenuController;
import com.mygdx.game.java.view.exceptions.InvalidName;
import com.mygdx.game.java.view.exceptions.NotEnoughMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ShopMenuTest {
    @Test
    public void wrongCardName() throws NotEnoughMoney, InvalidName {
        ShopMenuController.checkBuying("wrong name");
        Assertions.assertThrows(InvalidName.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                System.out.println("hi");
            }
        });
    }
}
