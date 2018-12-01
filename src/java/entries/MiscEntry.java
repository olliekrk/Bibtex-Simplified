package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.multiple;
import static entries.general.BibtexFieldConstraint.none;

/**
 * Container for @MISC type BibTeX entry.
 */
public class MiscEntry extends BibtexEntry {

    /**
     * All fields which are not ignored in BibTeX format of this entry type.
     * Their values are stored as {@link IBibtexValue}.
     */
    public IBibtexValue author, title, howpublished, month, year, note, key;

    /**
     * Returns id of this entry
     *
     * @param id of this entry
     */
    public MiscEntry(String id) {
        super(id);
    }

    /* filling constraint map with constraints */
    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : MiscEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", multiple);
        classConstraints.put(MiscEntry.class, constraintMap);
    }
}
