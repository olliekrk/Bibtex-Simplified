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
 * Container for @INBOOK type BibTeX entry.
 */
public class InbookEntry extends BibtexEntry {

    /**
     * All fields which are not ignored in BibTeX format of this entry type.
     * Their values are stored as {@link IBibtexValue}.
     */
    public IBibtexValue author, editor, title, chapter, pages, publisher, year, volume, number, series, type, address, edition, month, note, key;

    /**
     * Returns id of this entry
     *
     * @param id of this entry
     */
    public InbookEntry(String id) {
        super(id);
    }

    /* filling constraint map with constraints */
    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : InbookEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", alternativeMultiple);
        constraintMap.put("editor", alternativeMultiple);
        constraintMap.put("title", required);
        constraintMap.put("chapter", alternative);
        constraintMap.put("pages", alternative);
        constraintMap.put("publisher", required);
        constraintMap.put("year", required);
        classConstraints.put(InbookEntry.class, constraintMap);
    }

    /**
     * Method to validate entry - it is valid when it contains every field which is required
     * and at least one from every pair of alternative fields.
     *
     * @throws MissingRequiredEntryFieldException when entry is invalid
     * @throws IllegalAccessException             unlikely to happen
     */
    @Override
    public void validateEntry() throws MissingRequiredEntryFieldException, IllegalAccessException {
        super.validateEntry();
        if (author == null && editor == null) {
            throw new MissingRequiredEntryFieldException(this.getId(), "author", "editor");
        }
        if (chapter == null && pages == null) {
            throw new MissingRequiredEntryFieldException(this.getId(), "chapter", "pages");
        }
    }
}
