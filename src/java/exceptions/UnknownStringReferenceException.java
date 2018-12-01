package exceptions;

/**
 * Exception thrown by a {@link parser.BibtexParser} during parsing when it encountered
 * string reference to an unknown @ STRING entry.
 */
public class UnknownStringReferenceException extends ParsingException {
    /**
     * Thrown when string entry of given ID has not been found in {@link parser.BibtexBibliography}
     *
     * @param part id part which caused exception to be thrown
     */
    public UnknownStringReferenceException(String part) {
        super("Reference to an unknown @string of ID: '" + part + "' has been detected.");
    }
}
