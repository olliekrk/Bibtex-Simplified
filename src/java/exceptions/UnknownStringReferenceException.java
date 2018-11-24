package exceptions;

public class UnknownStringReferenceException extends ParsingException {
    public UnknownStringReferenceException(String part) {
        super("Reference to an unknown @string of ID: '" + part + "' has been detected.");
    }
}
