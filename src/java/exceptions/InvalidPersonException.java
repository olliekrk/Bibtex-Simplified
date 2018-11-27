package exceptions;

import values.IBibtexValue;

public class InvalidPersonException extends ParsingException {
    public InvalidPersonException(IBibtexValue value) {
        super("Unable to find at least one valid person in: " + value.getString());
    }

    public InvalidPersonException(String data) {
        super("Unable to interpret string: '" + data + "' as a person.");
    }
}
