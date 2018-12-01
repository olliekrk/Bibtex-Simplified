package exceptions;

import values.IBibtexValue;

/**
 * Exception thrown by a {@link values.MultipleValue} when format of a person field, such as 'author' or 'editor'
 * is invalid. Generally thrown when it was impossible to recognize string data as a person info.
 */
public class InvalidPersonException extends ParsingException {
    /**
     * Thrown when there has not been at least one valid person found in string data.
     *
     * @param value data which caused exception to be thrown
     */
    public InvalidPersonException(IBibtexValue value) {
        super("Unable to find at least one valid person in: " + value.getString());
    }

    /**
     * Thrown during parsing person field every time parser encounters data which was impossible to be
     * recognized as a person info.
     *
     * @param data data which caused exception to be thrown
     */
    public InvalidPersonException(String data) {
        super("Unable to interpret string: '" + data + "' as a person.");
    }
}
