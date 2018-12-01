package exceptions;

/**
 * Class of exceptions thrown by {@link parser.BibtexParser}
 */
public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }
}
