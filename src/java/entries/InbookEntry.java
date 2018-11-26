package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

public class InbookEntry extends BibtexEntry {

    public InbookEntry(String id) {
        super(id);
    }

    public IBibtexValue author, editor, title, chapter, pages, publisher, year, volume, number, series, type, address, edition, month, note, key;

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

    @Override
    public boolean validateEntry() {
        boolean a = author != null || editor != null;
        boolean b = chapter != null || pages != null;
        boolean c = super.validateEntry();
        return a && b && c;
    }
}
