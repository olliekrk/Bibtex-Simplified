package exceptions;

public class UnknownEntryTypeException extends ParsingException {
    public UnknownEntryTypeException(String entryType) {
        super("Could not recognize entry of unknown type: " + entryType);
    }
}
