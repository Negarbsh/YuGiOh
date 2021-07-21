package exceptions;

public class PreparationsNotChecked extends Exception {
    public PreparationsNotChecked() {
        super("Preparations of this effect aren't checked!");
    }
}
