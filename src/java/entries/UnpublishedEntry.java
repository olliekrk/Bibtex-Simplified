package entries;

import entries.general.BibtexEntry;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class UnpublishedEntry extends BibtexEntry {

    public UnpublishedEntry(String id) {
        super(id);
    }

    public IBibtexValue author, title, note, month, year, key;

    static {
        for (Field f : UnpublishedEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("note", required);
    }
}

