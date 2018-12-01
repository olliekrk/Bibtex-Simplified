package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

/**
 * Container for @ARTICLE type BibTeX entry.
 */
public class ArticleEntry extends BibtexEntry {

    /**
     * All fields which are not ignored in BibTeX format of this entry type.
     * Their values are stored as {@link IBibtexValue}.
     */
    public IBibtexValue author, title, journal, year, volume, number, pages, month, note, key;

    /**
     * Returns id of this entry
     *
     * @param id of this entry
     */
    public ArticleEntry(String id) {
        super(id);
    }

    /* filling constraint map with constraints */
    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : ArticleEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are is not any constraint on a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("journal", required);
        constraintMap.put("year", required);
        classConstraints.put(ArticleEntry.class, constraintMap);
    }

}
