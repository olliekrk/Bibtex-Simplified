package exceptions;

public class MissingRequiredEntryFieldException extends ParsingException {
    public MissingRequiredEntryFieldException(String id, String fieldName) {
        super("Entry of ID: " + id + " could not be parsed because it is missing a required field: " + fieldName + ".");
    }

    public MissingRequiredEntryFieldException(String id, String field1, String field2) {
        super("Entry of ID: " + id + " could not be parsed because it is missing both fields: " + field1 + " and " + field2 + ".");
    }
}
