package exceptions;

public class NotEnoughMoney extends Exception {
    public NotEnoughMoney() {
        super("you don't have enough money!");
    }
}
