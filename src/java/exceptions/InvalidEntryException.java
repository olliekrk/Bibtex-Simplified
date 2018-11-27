package exceptions;

public class InvalidEntryException extends ParsingException {

    public InvalidEntryException(String entryType) {
        super("Entry of type: @" + entryType + " is invalid and it was impossible to parse it.");
    }

    public InvalidEntryException(String entryType, String entryId) {
        super("Entry of type: @" + entryType + " has invalid ID: " + entryId + " and it was impossible to parse it.");
    }

    //thrown when encountered an entry with 0 fields
    public InvalidEntryException(String entryType, int i) {
        super("Entry of type: @" + entryType + " is invalid because it has " + i + " fields.");
    }
}
