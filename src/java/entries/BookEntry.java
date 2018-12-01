package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import exceptions.MissingRequiredEntryFieldException;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

/**
 * Container for @BOOK type BibTeX entry.
 */
public class BookEntry extends BibtexEntry {
    /**
     * All fields which are not ignored in BibTeX format of this entry type.
     * Their values are stored as {@link IBibtexValue}.
     */
    public IBibtexValue author, editor, title, publisher, year, volume, number, series, address, edition;

    /**
     * Returns id of this entry
     *
     * @param id of this entry
     */
    public BookEntry(String id) {
        super(id);
    }

    /* filling constraint map with constraints */
    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : BookEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", alternativeMultiple);
        constraintMap.put("editor", alternativeMultiple);
        constraintMap.put("title", required);
        constraintMap.put("publisher", required);
        constraintMap.put("year", required);
        classConstraints.put(BookEntry.class, constraintMap);
    }

    /**
     * Method to validate entry - it is valid when it contains every field which is required
     * and at least one from every pair of alternative fields.
     *
     * @throws MissingRequiredEntryFieldException when entry is invalid
     * @throws IllegalAccessException             unlikely to happen, present because of use of Reflection
     */
    @Override
    public void validateEntry() throws MissingRequiredEntryFieldException, IllegalAccessException {
        super.validateEntry();
        //author and editor are alternatively required in this entry type
        if (author == null && editor == null) {
            throw new MissingRequiredEntryFieldException(this.getId(), "author", "editor");
        }
    }
}
