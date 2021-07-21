package exceptions;

public class NotAppropriateCard extends Exception {
    public NotAppropriateCard(String cardType) {
        super(String.format("only %s cards can be selected!", cardType));
    }
}
