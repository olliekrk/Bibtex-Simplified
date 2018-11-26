package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

public class IncollectionEntry extends BibtexEntry {

    public IncollectionEntry(String id) {
        super(id);
    }

    public IBibtexValue author, title, booktitle, publisher, year, editor, volume, number, series, type, chapter, pages, address, edition, month, note, key;

    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : IncollectionEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("booktitle", required);
        constraintMap.put("publisher", required);
        constraintMap.put("year", required);
        constraintMap.put("editor", multiple);
        classConstraints.put(IncollectionEntry.class, constraintMap);
    }
}
