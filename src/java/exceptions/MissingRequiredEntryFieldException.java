package exceptions;

import entries.general.BibtexEntry;

public class MissingRequiredEntryFieldException extends ParsingException  {
    public MissingRequiredEntryFieldException(BibtexEntry entry) {
        super ("Entry of ID: " + entry.getId() + " could not be parsed because it is missing a required field.");
    }
}
