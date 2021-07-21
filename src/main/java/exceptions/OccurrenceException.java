package exceptions;


public class OccurrenceException extends Exception {
    public OccurrenceException(int limit, String cardName, String deckName) {
        super(String.format("there are already %d cards with name %s in deck %s",
               limit, cardName, deckName ));
    }
}
