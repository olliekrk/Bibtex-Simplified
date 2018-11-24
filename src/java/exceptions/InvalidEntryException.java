package exceptions;

public class InvalidEntryException extends ParsingException {

    public InvalidEntryException(String entryType) {
        super("Entry of type: @" + entryType + " is invalid and it was impossible to parse it.");
    }

    public InvalidEntryException(String entryId, String entryType) {
        super("Entry of type: @" + entryType + " has invalid ID: " + entryId + " and it was impossible to parse it.");
    }
}
