package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

/**
 * Container for @INPROCEEDINGS type BibTeX entry.
 */
public class InproceedingsEntry extends BibtexEntry {

    /**
     * All fields which are not ignored in BibTeX format of this entry type.
     * Their values are stored as {@link IBibtexValue}.
     */
    public IBibtexValue author, title, booktitle, year, editor, volume, number, series, pages, address, month, organization, publisher, note, key;

    /**
     * Returns id of this entry
     *
     * @param id of this entry
     */
    public InproceedingsEntry(String id) {
        super(id);
    }

    /* filling constraint map with constraints */
    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : InproceedingsEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("booktitle", required);
        constraintMap.put("year", required);
        constraintMap.put("editor", multiple);
        classConstraints.put(InproceedingsEntry.class, constraintMap);
    }
}
