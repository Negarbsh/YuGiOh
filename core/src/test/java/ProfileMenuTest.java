//fully tested profile menu main.java.controller


import main.java.controller.ProfileMenuController;
import main.java.view.exceptions.*;
import main.java.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ProfileMenuTest {  //main.java.controller

    @Test
    @DisplayName("change nickname")
    public void changeNickname1() throws WrongMenu, AlreadyExistingError, WrongPassword, EqualPasswordException, InvalidCommand {
        User user = new User("hasti", "123", "hk");
        ProfileMenuController.setUser(user);
        ProfileMenuController.changeCommands("--nickname hasti2");
        Assertions.assertEquals("hasti2", user.getNickName());
    }

    @Test
    @DisplayName("equal nicknames")
    public void changeNickname2() throws WrongMenu, AlreadyExistingError, WrongPassword, EqualPasswordException, InvalidCommand {
        User user = new User("hasti", "123", "hk");
        new User("a", "123", "hasti");
        ProfileMenuController.setUser(user);
        Executable commands = new Executable() {
            @Override
            public void execute() throws Throwable {
                ProfileMenuController.changeCommands("--nickname hasti");
            }
        };

        Assertions.assertThrows(AlreadyExistingError.class, commands);
    }

    @Test
    @DisplayName("change password")
    public void changePassword1() throws WrongMenu, AlreadyExistingError, WrongPassword, EqualPasswordException, InvalidCommand {
        User user = new User("hasti", "123", "hk");
        ProfileMenuController.setUser(user);
        Assertions.assertEquals("123",user.getPassword());
        ProfileMenuController.changeCommands("--password --current 123 --new 1234");
        Assertions.assertEquals("1234", user.getPassword());

        Assertions.assertThrows(EqualPasswordException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ProfileMenuController.changeCommands("--password --current 1234 --new 1234");
            }
        });

        Assertions.assertThrows(WrongPassword.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ProfileMenuController.changeCommands("--password --current hello --new 1234");
            }
        });
    }
}
