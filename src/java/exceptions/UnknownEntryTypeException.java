package exceptions;

/**
 * Exception thrown by a {@link parser.BibtexParser} during parsing when it encounters
 * an entry of type unknown to parser.
 */
public class UnknownEntryTypeException extends ParsingException {
    /**
     * Thrown when parser could not recognize entry of some type.
     *
     * @param entryType type which failed to be recognized by parser
     */
    public UnknownEntryTypeException(String entryType) {
        super("Could not recognize entry of unknown type: " + entryType);
    }
}
