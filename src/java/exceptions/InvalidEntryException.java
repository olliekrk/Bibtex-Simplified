package exceptions;

/**
 * Exception thrown by a {@link parser.BibtexParser} when there was encountered a problem during parsing an entry data.
 */
public class InvalidEntryException extends ParsingException {

    /**
     * Thrown when ID of an entry is invalid.
     *
     * @param entryType type of entry
     * @param entryId   invalid entry ID which caused exception to be thrown
     */
    public InvalidEntryException(String entryType, String entryId) {
        super("Entry of type: @" + entryType + " has invalid ID: " + entryId + " and it was impossible to parse it.");
    }

    /**
     * Thrown when encountered an entry with zero or one field.
     *
     * @param entryType type of entry
     * @param i         number of entry fields
     */
    public InvalidEntryException(String entryType, int i) {
        super("Entry of type: @" + entryType + " is invalid because it has " + i + " fields.");
    }

    /**
     * Thrown when encountered an entry data (entry's body) which was impossible to parse.
     *
     * @param entryType type of entry
     * @param unknown   parameter just to differ this constructor from that which takes two strings as params
     * @param entryData data (body) of entry
     */
    public InvalidEntryException(String entryType, String unknown, String entryData) {
        super("Entry of type: @" + entryType + " is invalid and it was impossible to parse it: " + entryData);
    }
}
