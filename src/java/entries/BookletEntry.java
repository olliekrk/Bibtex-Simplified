package entries;

import entries.general.BibtexEntry;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class BookletEntry extends BibtexEntry {

    public BookletEntry(String id) {
        super(id);
    }

    public IBibtexValue title, author, howpublished, address, month, year, note, key;

    static {
        for (Field f : BookletEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("title", required);
        constraintMap.put("author", multiple);
    }
}
