package exceptions;

/**
 * Thrown by a {@link entries.general.BibtexEntry} during validation of an parsed entry
 * when entry is missing at least one required or both alternatively required fields.
 */
public class MissingRequiredEntryFieldException extends ParsingException {
    /**
     * Thrown when entry is missing required field.
     *
     * @param id        id of entry which is missing a field
     * @param fieldName name of field which is missing
     */
    public MissingRequiredEntryFieldException(String id, String fieldName) {
        super("Entry of ID: " + id + " could not be parsed because it is missing a required field: " + fieldName + ".");
    }

    /**
     * Thrown when entry is missing both fields that have constraint 'alternative' or 'alternativeMultiple'.
     *
     * @param id     id of entry which caused exception to be thrown
     * @param field1 name of first field which is missing
     * @param field2 name of second field which is missing
     * @see entries.general.BibtexFieldConstraint
     */
    public MissingRequiredEntryFieldException(String id, String field1, String field2) {
        super("Entry of ID: " + id + " could not be parsed because it is missing both fields: " + field1 + " and " + field2 + ".");
    }
}
