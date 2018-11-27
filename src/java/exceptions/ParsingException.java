package exceptions;

public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, String id) {
        super("Exception at entry of ID: " + id + "\n" + message);
    }
}
