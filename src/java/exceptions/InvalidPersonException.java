package exceptions;

public class InvalidPersonException extends ParsingException {
    public InvalidPersonException(String data) {
        super("Unable to interpret string: '" + data + "' as a person.");
    }
}
