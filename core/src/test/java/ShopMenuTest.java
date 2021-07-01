import main.java.controller.ShopMenuController;
import main.java.view.exceptions.InvalidName;
import main.java.view.exceptions.NotEnoughMoney;
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
